package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

public interface MainInteractor extends Interactor<MainState, Router> {

    @UiThread
    void navigateContacts();

    @UiThread
    void forceContactsPermissions();
}
