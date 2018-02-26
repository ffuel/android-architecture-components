package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleInteractor;
import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65apps.architecturecomponents.presentation.main.MainRouter;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.presentation.presenter.BasePresenter;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class SamplePresenter extends BasePresenter<SampleState, SampleView, SampleInteractor, MainRouter> {

    @Inject
    public SamplePresenter(@NonNull ExecutorsFactory executors, @NonNull MainRouter router,
                           @NonNull SampleInteractor interactor, @NonNull ApplicationLogger logger) {
        super(executors, router, interactor, logger);
    }
}
