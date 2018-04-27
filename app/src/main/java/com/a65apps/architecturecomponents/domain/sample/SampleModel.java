package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.model.ReloadingModel;
import com.a65aps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import javax.inject.Inject;

import io.reactivex.Single;

public final class SampleModel extends ReloadingModel<SampleState, Router>
        implements SampleInteractor {

    @NonNull
    private final SampleSource source;

    @Inject
    SampleModel(@NonNull SampleSource source,
                @NonNull ConnectionReceiverSource connectionSource,
                @NonNull ExecutorsFactory executors,
                @NonNull Router router) {
        super(SampleState.create(SampleState.State.LOADING, source.text(), "", ""),
                router, connectionSource, executors.getExecutor(SchedulerType.IO));
        this.source = source;
    }

    @NonNull
    @Override
    protected Single<SampleState> getData() {
        return source.data().map(data -> getState().mutateData(data));
    }

    @NonNull
    @Override
    protected SampleState getError(@NonNull SampleState lastState, @NonNull Throwable error) {
        return lastState.mutateError(error.getMessage());
    }

    @NonNull
    @Override
    protected SampleState onConnectionChanged(@NonNull SampleState lastState, boolean isConnected) {
        SampleState.State sampleState = !isConnected
                ? SampleState.State.ERROR : lastState.state() != SampleState.State.ERROR
                ? lastState.state() : SampleState.State.COMPLETE;
        return SampleState.create(
                sampleState,
                lastState.text(),
                lastState.data(),
                sampleState == SampleState.State.ERROR
                        ? source.noConnectionText() : "");
    }

    @NonNull
    @Override
    protected SampleState loadingState(@NonNull SampleState lastState) {
        return lastState.mutateLoading(source.text());
    }

    @NonNull
    @Override
    protected SampleState getConnectionError(@NonNull SampleState lastState) {
        return lastState.mutateError(source.noConnectionText());
    }
}
