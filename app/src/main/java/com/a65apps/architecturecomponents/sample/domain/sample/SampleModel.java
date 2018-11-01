package com.a65apps.architecturecomponents.sample.domain.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.model.ReloadingModel;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;

import javax.inject.Inject;

import io.reactivex.Single;

public final class SampleModel extends ReloadingModel<SampleState, Router> {

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
    protected Single<SampleState> tryGetDataCached() {
        return source.cachedData().map(data -> {
            if (data.isEmpty()) {
                SampleState state = getState();
                return state.mutateError(state.error());
            }

            return getState().mutateData(data);
        });
    }

    @NonNull
    @Override
    protected SampleState getError(@NonNull SampleState lastState, @NonNull Throwable error) {
        return lastState.mutateError(error.getMessage());
    }

    @NonNull
    @Override
    protected SampleState onConnectionChanged(@NonNull SampleState lastState, boolean isConnected) {
        SampleState.State sampleState;
        if (!isConnected) {
            sampleState = SampleState.State.ERROR;
        } else {
            sampleState = lastState.state() != SampleState.State.ERROR ? lastState.state()
                    : SampleState.State.COMPLETE;
        }
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
        return lastState.mutateErrorPreserveCurrentState(source.noConnectionText());
    }
}
