package com.a65aps.architecturecomponents.domain.model;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65aps.architecturecomponents.domain.receiver.ConnectionState;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import net.jcip.annotations.NotThreadSafe;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

@NotThreadSafe
public abstract class ReloadingModel<S extends ReloadingState, R extends Router>
        extends BaseModel<S, R> implements ReloadingInteractor<S, R> {

    @NonNull
    private final ConnectionReceiverSource connectionSource;
    @NonNull
    private final ThreadExecutor executor;

    @NonNull
    private final Subject<Boolean> loadSubject = BehaviorSubject.createDefault(true).toSerialized();

    public ReloadingModel(@NonNull S defaultState, @NonNull R router,
                          @NonNull ConnectionReceiverSource connectionSource,
                          @NonNull ThreadExecutor executor) {
        super(defaultState, router);
        this.connectionSource = connectionSource;
        this.executor = executor;
    }

    @Override
    @CallSuper
    public void firstStart(boolean isRestoring) {
        addDisposable(reloadEvent()
                .doOnNext(this::setState)
                .doOnError(this::setError)
                .subscribe());
        addDisposable(observeConnectionState()
                .doOnNext(this::setState)
                .doOnError(this::setError)
                .subscribe());
    }

    @Override
    @CallSuper
    public void restoreState(@NonNull S state) {
        super.restoreState(state);

        if (state.isLoading()) {
            reload();
        }
    }

    @Override
    public void reload() {
        loadSubject.onNext(true);
    }

    @NonNull
    protected abstract Single<S> getData();

    @NonNull
    protected abstract Single<S> tryGetDataCached();

    @NonNull
    protected abstract S getError(@NonNull S lastState, @NonNull Throwable error);

    @NonNull
    protected abstract S onConnectionChanged(@NonNull S lastState, boolean isConnected);

    @NonNull
    protected abstract S loadingState(@NonNull S lastState);

    @NonNull
    protected abstract S getConnectionError(@NonNull S lastState);

    @NonNull
    private Observable<S> observeConnectionState() {
        return connectionSource.observeReceiver()
                .doOnNext(connection -> {
                    S state = getState();
                    if (connection.isConnected() && !state.isLoading() && !state.hasData()) {
                        reload();
                    }
                })
                .map(connection -> onConnectionChanged(getState(), connection.isConnected()));
    }

    @NonNull
    private Single<S> loadData() {
        return getData()
                .doOnSubscribe(__ -> setLoading())
                .doOnError(this::setError);
    }

    @NonNull
    private Single<S> tryLoadDataCache() {
        return tryGetDataCached()
                .doOnSubscribe(__ -> setLoading())
                .doOnError(this::setError);
    }

    @NonNull
    private Observable<S> reloadEvent() {
        return loadSubject
                .flatMapSingle(__ -> connectionSource.single())
                .observeOn(executor.getScheduler())
                .map(ConnectionState::isConnected)
                .flatMapSingle(connected -> {
                    if (connected) {
                        return loadData();
                    }

                    setState(getConnectionError(getState()));
                    return tryLoadDataCache();
                });
    }

    private void setError(@NonNull Throwable error) {
        setState(getError(getState(), error));
    }

    private void setLoading() {
        setState(loadingState(getState()));
    }
}
