package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.model.BaseModel;
import com.a65aps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65aps.architecturecomponents.domain.receiver.ConnectionState;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public final class SampleModel extends BaseModel<SampleState, Router> implements SampleInteractor {

    @NonNull
    private final ConnectionReceiverSource connectionSource;
    @NonNull
    private final SampleSource source;
    @NonNull
    private final Subject<Boolean> loadSubject = BehaviorSubject.createDefault(true).toSerialized();
    @NonNull
    private final ThreadExecutor ioExecutor;

    @Inject
    SampleModel(@NonNull SampleSource source,
                @NonNull ConnectionReceiverSource connectionSource,
                @NonNull ExecutorsFactory executors,
                @NonNull Router router) {
        super(SampleState.create(SampleState.State.LOADING, source.text(), "", ""), router);
        this.source = source;
        this.connectionSource = connectionSource;
        this.ioExecutor = executors.getExecutor(SchedulerType.IO);
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
    public void reload() {
        loadSubject.onNext(true);
    }

    @NonNull
    private Observable<SampleState> observeConnectionState() {
        return connectionSource.observeReceiver()
                .map(connection -> {
                    SampleState state = getState();
                    SampleState.State sampleState = !connection.isConnected()
                            ? SampleState.State.ERROR : state.state() != SampleState.State.ERROR
                            ? state.state() : SampleState.State.COMPLETE;
                    return SampleState.create(
                            sampleState,
                            state.text(),
                            state.data(),
                            sampleState == SampleState.State.ERROR
                            ? source.noConnectionText() : "");
                });
    }

    @NonNull
    private Single<SampleState> loadData() {
        return source.data()
                .doOnSubscribe(__ -> setLoading())
                .doOnError(this::setError)
                .map(data -> getState().mutateData(data));
    }

    @NonNull
    private Observable<SampleState> reloadEvent() {
        return loadSubject
                .flatMapSingle(__ -> connectionSource.single())
                .observeOn(ioExecutor.getScheduler())
                .map(ConnectionState::isConnected)
                .flatMapSingle(connected -> {
                    if (connected) {
                        return loadData();
                    }

                    return Single.just(getState().mutateError(source.noConnectionText()));
                });
    }

    private void setError(@NonNull Throwable error) {
        setState(getError(error));
    }

    private void setLoading() {
        setState(getState().mutateLoading(source.text()));
    }

    @NonNull
    private SampleState getError(@NonNull Throwable error) {
        return getState().mutateError(error.getMessage());
    }
}
