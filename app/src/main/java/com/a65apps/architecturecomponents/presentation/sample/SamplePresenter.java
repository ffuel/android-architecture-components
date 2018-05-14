package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainInteractor;
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

    @NonNull
    private final MainInteractor mainInteractor;

    @Inject
    SamplePresenter(@NonNull ExecutorsFactory executors,
                    @NonNull SampleInteractor interactor,
                    @NonNull ApplicationLogger logger, @NonNull MainInteractor mainInteractor) {
        super(executors, interactor, logger);
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void onBackPressed() {
        mainInteractor.onBack();
    }

    void refresh() {
        getInteractor().reload();
    }
}
