package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65apps.architecturecomponents.domain.main.Screen;
import com.a65apps.architecturecomponents.presentation.navigation.Screens;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.presentation.presenter.BasePresenter;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends BasePresenter<MainState, MainView, MainInteractor, MainRouter> {

    @Inject
    public MainPresenter(@NonNull ExecutorsFactory executors, @NonNull MainRouter router,
                         @NonNull MainInteractor interactor, @NonNull ApplicationLogger logger) {
        super(executors, router, interactor, logger);
    }

    void updateScreen(@NonNull Screen screen) {
        switch (screen) {
            case SAMPLE:
                getRouter().newRootScreenIfNotExists(Screens.SAMPLE_SCREEN);
                break;
        }
    }
}
