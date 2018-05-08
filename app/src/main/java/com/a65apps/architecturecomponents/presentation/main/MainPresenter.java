package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.moxyarchitecturecomponents.presenter.MoxyPresenter;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class MainPresenter extends MoxyPresenter<MainState, MainView, MainInteractor, Router> {

    @Inject
    MainPresenter(@NonNull ExecutorsFactory executors, @NonNull MainInteractor interactor,
                         @NonNull ApplicationLogger logger) {
        super(executors, interactor, logger);
    }

    void showContacts(@NonNull PermissionsSource permissionsSource) {
        getInteractor().navigateContacts(permissionsSource);
    }

    void forceContactsPermissions(@NonNull PermissionsSource permissionsSource) {
        getInteractor().forceContactsPermissions(permissionsSource);
    }
}
