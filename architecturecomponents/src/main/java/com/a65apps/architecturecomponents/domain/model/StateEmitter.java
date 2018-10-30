package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;

import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;

@ThreadSafe
public final class StateEmitter<T extends State> implements ObservableOnSubscribe<T>,
        AppendOnlyList.Predicate<Object> {

    private static final int CAPACITY = 4;

    @NonNull
    private final Set<ObservableEmitter<T>> emitters = new HashSet<>();

    @NonNull
    private volatile T state;
    @NonNull
    private final ReadWriteLock readWriteLock;
    @Nullable
    private AppendOnlyList<? super Event> queue;
    private boolean emitting;

    public static <T extends State> StateEmitter<T> create(@NonNull T state) {
        return new StateEmitter<>(state);
    }

    private StateEmitter(@NonNull T state) {
        this.state = Objects.requireNonNull(state);
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public void subscribe(final ObservableEmitter<T> emitter) {
        emitter.setDisposable(Disposables.fromAction(() -> {
            readWriteLock.writeLock().lock();
            try {
                if (emitting) {
                    AppendOnlyList<? super Event> q = queue;
                    if (q == null) {
                        q = new AppendOnlyList<>(CAPACITY);
                        queue = q;
                    }
                    q.add(new DisposeEvent<>(emitter));
                    return;
                }
                emitting = true;
                emitters.remove(emitter);
            } finally {
                readWriteLock.writeLock().unlock();
            }

            emitLoop();
        }));

        readWriteLock.writeLock().lock();
        try {
            if (emitting) {
                AppendOnlyList<? super Event> q = queue;
                if (q == null) {
                    q = new AppendOnlyList<>(CAPACITY);
                    queue = q;
                }
                q.add(new SubscribeEvent<>(emitter));
                return;
            }
            emitting = true;
            emitters.add(emitter);
            emit(state, emitter);
        } finally {
            readWriteLock.writeLock().unlock();
        }

        emitLoop();
    }

    public void setState(@NonNull T state) {
        readWriteLock.writeLock().lock();
        try {
            if (emitting) {
                AppendOnlyList<? super Event> q = queue;
                if (q == null) {
                    q = new AppendOnlyList<>(CAPACITY);
                    queue = q;
                }
                q.add(new EmitEvent<>(state));
                return;
            }
            emitting = true;
            this.state = state;
            deliverState(state);
        } finally {
            readWriteLock.writeLock().unlock();
        }

        emitLoop();
    }

    @NonNull
    public T getState() {
        readWriteLock.readLock().lock();
        try {
            return this.state;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean test(@NonNull Object event) {
        if (event instanceof SubscribeEvent) {
            readWriteLock.writeLock().lock();
            try {
                SubscribeEvent<T> subscribeEvent = (SubscribeEvent<T>) event;
                emit(state, subscribeEvent.getEmitter());
            } finally {
                readWriteLock.writeLock().unlock();
            }
            return false;
        }
        if (event instanceof DisposeEvent) {
            DisposeEvent<T> disposeEvent = (DisposeEvent<T>) event;
            readWriteLock.writeLock().lock();
            try {
                emitters.remove(disposeEvent.getEmitter());
            } finally {
                readWriteLock.writeLock().unlock();
            }
            return false;
        }

        EmitEvent<T> emitEvent = (EmitEvent<T>) event;
        readWriteLock.writeLock().lock();
        try {
            this.state = emitEvent.getState();
            deliverState(emitEvent.getState());
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return false;
    }

    private void emitLoop() {
        for (;;) {
            AppendOnlyList<? super Event> q;
            readWriteLock.writeLock().lock();
            try {
                q = queue;
                if (q == null) {
                    emitting = false;
                    return;
                }
                queue = null;
            } finally {
                readWriteLock.writeLock().unlock();
            }
            q.forEachWhile(this);
        }
    }

    private void deliverState(@NonNull T state) {
        for (ObservableEmitter<T> emitter: emitters) {
            emit(state, emitter);
        }
    }

    private void emit(@NonNull T state, @Nullable ObservableEmitter<T> emitter) {
        if (emitter == null || emitter.isDisposed()) {
            return;
        }
        emitter.onNext(state);
    }

    private interface Event {

    }

    private static final class SubscribeEvent<T> implements Event {

        @NonNull
        private final ObservableEmitter<T> emitter;

        private SubscribeEvent(@NonNull ObservableEmitter<T> emitter) {
            this.emitter = emitter;
        }

        @NonNull
        ObservableEmitter<T> getEmitter() {
            return emitter;
        }
    }

    private static final class DisposeEvent<T> implements Event {

        @NonNull
        private final ObservableEmitter<T> emitter;

        private DisposeEvent(@NonNull ObservableEmitter<T> emitter) {
            this.emitter = emitter;
        }

        @NonNull
        ObservableEmitter<T> getEmitter() {
            return emitter;
        }
    }

    private static final class EmitEvent<T> implements Event {

        @NonNull
        private final T state;

        private EmitEvent(@NonNull T state) {
            this.state = state;
        }

        @NonNull
        public T getState() {
            return state;
        }
    }
}
