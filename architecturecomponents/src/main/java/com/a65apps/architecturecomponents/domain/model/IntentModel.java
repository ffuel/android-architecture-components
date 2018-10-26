package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.FlowableIntent;
import com.a65apps.architecturecomponents.domain.Intent;
import com.a65apps.architecturecomponents.domain.IntentFactory;
import com.a65apps.architecturecomponents.domain.MaybeIntent;
import com.a65apps.architecturecomponents.domain.ObservableIntent;
import com.a65apps.architecturecomponents.domain.SingleIntent;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.StateProvider;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import net.jcip.annotations.ThreadSafe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

@ThreadSafe
public class IntentModel<S extends State, R extends Router> extends BaseModel<S, R> implements IntentInteractor<S, R> {

    @NonNull
    private final IntentFactory intentFactory;
    @NonNull
    private final ApplicationLogger logger;
    @NonNull
    private final Map<String, Disposable> disposableMap = Collections.synchronizedMap(new HashMap<>());

    @SuppressWarnings("WeakerAccess")
    @Inject
    public IntentModel(@NonNull StateProvider<S> stateProvider, @NonNull R router, @NonNull IntentFactory intentFactory,
                       @NonNull ApplicationLogger logger) {
        super(stateProvider.provide(), router);
        this.intentFactory = intentFactory;
        this.logger = logger;
    }

    @Override
    public void execute(@NonNull String type) {
        execute(type, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(@NonNull String type, @Nullable Object data) {
        Intent intent = intentFactory.get(type);
        R router = getRouter();
        Disposable disposable = null;
        if (intent instanceof SingleIntent) {
            disposable = processSingleIntent((SingleIntent<S, R>) intent, router, data);
        } else if (intent instanceof ObservableIntent) {
            disposable = processObservableIntent((ObservableIntent<S, R>) intent, router, data);
        } else if (intent instanceof MaybeIntent) {
            disposable = processMaybeIntent((MaybeIntent<S, R>) intent, router, data);
        } else if (intent instanceof FlowableIntent) {
            disposable = processFlowableIntent((FlowableIntent<S, R>) intent, router, data);
        }

        if (disposable != null) {
            dispose(type);
        }
        disposableMap.put(type, disposable);
    }

    @Override
    public void execute(@NonNull Collection<Command> collection) {
        for (Command command: collection) {
            execute(command.getType(), command.getData());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void chainExecute(@NonNull Collection<Command> collection) {
        addDisposable(Observable.fromIterable(collection)
                .map(command -> new IntentAndData(intentFactory.get(command.getType()), command.getData()))
                .flatMapSingle(data -> {
                    if (data.getIntent() instanceof SingleIntent) {
                        return singleIntent((SingleIntent<S, R>) data.getIntent(), getRouter(), data.getData());
                    }

                    return Single.error(new IllegalArgumentException("Unsupported intent type. "
                            + "Chain execution supports only SingleIntent"));
                })
                .subscribe(it -> {
                }, logger::logError));
    }

    @Override
    public void dispose(@NonNull String type) {
        Disposable disposable = disposableMap.get(type);
        unsubscribe(disposable);
    }

    @Override
    public void dispose() {
        synchronized (disposableMap) {
            for (Disposable disposable : disposableMap.values()) {
                unsubscribe(disposable);
            }
        }

        super.dispose();
    }

    @NonNull
    private Disposable processSingleIntent(SingleIntent<S, R> intent, @NonNull R router, @Nullable Object data) {
        return intent.execute(this, router, data)
                .onErrorResumeNext(error -> intent.onError(error, this, router))
                .subscribe(this::onStateChanged, logger::logError);
    }

    @NonNull
    private Single<S> singleIntent(SingleIntent<S, R> intent, @NonNull R router, @Nullable Object data) {
        return intent.execute(this, router, data)
                .onErrorResumeNext(error -> intent.onError(error, this, router))
                .doOnSuccess(this::onStateChanged);
    }

    @SuppressWarnings("squid:S1602")
    @NonNull
    private Disposable processObservableIntent(ObservableIntent<S, R> intent, @NonNull R router,
                                               @Nullable Object data) {
        return intent.execute(this, router, data)
                .onErrorResumeNext(error -> {
                    return intent.onError(error, this, router);
                })
                .subscribe(this::onStateChanged, logger::logError);
    }

    @SuppressWarnings("squid:S1602")
    @NonNull
    private Disposable processMaybeIntent(MaybeIntent<S, R> intent, @NonNull R router, @Nullable Object data) {
        return intent.execute(this, router, data)
                .onErrorResumeNext(error -> {
                    return intent.onError(error, this, router);
                })
                .subscribe(this::onStateChanged, logger::logError);
    }

    @SuppressWarnings("squid:S1602")
    @NonNull
    private Disposable processFlowableIntent(FlowableIntent<S, R> intent, @NonNull R router, @Nullable Object data) {
        return intent.execute(this, router, data)
                .onErrorResumeNext(error -> {
                    return intent.onError(error, this, router);
                })
                .subscribe(this::onStateChanged, logger::logError);
    }

    private void onStateChanged(@NonNull S state) {
        setState(state);
    }

    private void unsubscribe(@Nullable Disposable disposable) {
        if (disposable == null || disposable.isDisposed()) {
            return;
        }

        disposable.dispose();
    }

    private static final class IntentAndData {

        @NonNull
        private final Intent intent;
        @Nullable
        private final Object data;

        private IntentAndData(@NonNull Intent intent, @Nullable Object data) {
            this.intent = intent;
            this.data = data;
        }

        @NonNull
        public Intent getIntent() {
            return intent;
        }

        @Nullable
        public Object getData() {
            return data;
        }
    }
}
