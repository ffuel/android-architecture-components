package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;

import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;

@ThreadSafe
public final class StateEmitter<T extends State> implements ObservableOnSubscribe<T> {

    @NonNull
    private final Set<ObservableEmitter<T>> emitters = new HashSet<>();

    @NonNull
    private volatile T state;
    @NonNull
    private final ReadWriteLock readWriteLock;

    public static <T extends State> StateEmitter<T> create(@NonNull T state) {
        return new StateEmitter<>(state);
    }

    private StateEmitter(@NonNull T state) {
        this.state = state;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public void subscribe(final ObservableEmitter<T> emitter) {
        emitter.setDisposable(Disposables.fromAction(() -> {
            readWriteLock.writeLock().lock();
            try {
                emitters.remove(emitter);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }));

        readWriteLock.writeLock().lock();
        try {
            emitters.add(emitter);
            emit(state, emitter);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void setState(@NonNull T state) {
        readWriteLock.writeLock().lock();
        try {
            this.state = state;
            deliverState(state);
        } finally {
            readWriteLock.writeLock().unlock();
        }
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
}
