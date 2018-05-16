package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

public interface MainInteractor extends Interactor<MainState, Router> {

    @UiThread
    void navigateContacts();

    @UiThread
    void forceContactsPermissions();

    @UiThread
    void onBack();
}
