package com.a65apps.architecturecomponents.sample.domain.mvi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.SingleIntent;
import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.sample.R;

import javax.inject.Inject;

import io.reactivex.Single;

class SubtitleIntent implements SingleIntent<MviState, Router> {

    @NonNull
    private final StringResources stringResources;
    @NonNull
    private final ThreadExecutor ioExecutor;

    @Inject
    SubtitleIntent(@NonNull StringResources stringResources, @NonNull ExecutorsFactory executors) {
        this.stringResources = stringResources;
        this.ioExecutor = executors.getExecutor(SchedulerType.IO);
    }

    @NonNull
    @Override
    public Single<MviState> execute(@NonNull IntentInteractor<MviState, Router> interactor, @NonNull Router router,
                                    @Nullable Object data) {
        return interactor.observeState().firstOrError()
                .map(state -> state.mutateSubtitle(stringResources.getString(R.string.mvi_subtitle)))
                .subscribeOn(ioExecutor.getScheduler());
    }

    @NonNull
    @Override
    public Single<MviState> onError(@NonNull Throwable error, @NonNull IntentInteractor<MviState, Router> interactor,
                                    @NonNull Router router) {
        return interactor.observeState().firstOrError()
                .map(state -> state.mutateSubtitle(error.getMessage()))
                .subscribeOn(ioExecutor.getScheduler());
    }
}
