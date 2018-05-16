package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.model.ReloadingInteractor;
import com.a65apps.architecturecomponents.sample.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.sample.domain.sample.SampleState;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.moxyarchitecturecomponents.presenter.MoxyPresenter;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class SamplePresenter extends MoxyPresenter<SampleState, MoxyView<SampleState>,
        ReloadingInteractor<SampleState, Router>, Router> {

    @NonNull
    private final MainInteractor mainInteractor;

    @Inject
    SamplePresenter(@NonNull ExecutorsFactory executors,
                    @NonNull ReloadingInteractor<SampleState, Router> interactor,
                    @NonNull ApplicationLogger logger, @NonNull MainInteractor mainInteractor) {
        super(executors, interactor, logger);
        this.mainInteractor = mainInteractor;
    }

    @UiThread
    @Override
    public void onBackPressed() {
        mainInteractor.onBack();
    }

    @UiThread
    void refresh() {
        getInteractor().reload();
    }
}
