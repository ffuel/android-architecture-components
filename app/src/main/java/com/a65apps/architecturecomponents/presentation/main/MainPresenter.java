package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterSubComponentBuilderFactory;
import com.a65aps.moxyarchitecturecomponents.presenter.MoxyPresenter;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends MoxyPresenter<MainState, MainView, MainInteractor, Router> {

    @NonNull
    private final PresenterSubComponentBuilderFactory componentFactory;

    @Inject
    MainPresenter(@NonNull ExecutorsFactory executors, @NonNull MainInteractor interactor,
                  @NonNull ApplicationLogger logger,
                  @NonNull PresenterSubComponentBuilderFactory componentFactory) {
        super(executors, interactor, logger);
        this.componentFactory = componentFactory;
    }

    @Override
    public void onBackPressed() {
        getInteractor().onBack();
    }

    void showContacts() {
        getInteractor().navigateContacts();
    }

    void forceContactsPermissions() {
        getInteractor().forceContactsPermissions();
    }

    @NonNull
    PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }
}
