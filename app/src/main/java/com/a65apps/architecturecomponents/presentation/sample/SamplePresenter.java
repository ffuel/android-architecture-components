package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleInteractor;
import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.moxyarchitecturecomponents.presenter.MoxyPresenter;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class SamplePresenter extends MoxyPresenter<SampleState, SampleView, SampleInteractor, Router> {

    @Inject
    SamplePresenter(@NonNull ExecutorsFactory executors,
                    @NonNull SampleInteractor interactor,
                    @NonNull ApplicationLogger logger) {
        super(executors, interactor, logger);
    }

    void refresh() {
        getInteractor().reload();
    }
}
