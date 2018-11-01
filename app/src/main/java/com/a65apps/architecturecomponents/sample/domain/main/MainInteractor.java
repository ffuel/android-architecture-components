package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;

import io.reactivex.Observable;

public interface MainInteractor extends Interactor<MainState, Router> {

    @UiThread
    void navigateContacts();

    @UiThread
    void forceContactsPermissions();

    @UiThread
    void onBack();

    @UiThread
    void navigatePosts();

    @UiThread
    void navigateMvi();

    @NonNull
    Observable<String> observeSystemMessages();

    void broadcastSystemMessage(@NonNull String message);
}
