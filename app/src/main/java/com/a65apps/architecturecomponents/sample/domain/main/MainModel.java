package com.a65apps.architecturecomponents.sample.domain.main;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.domain.model.BaseModel;
import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.sample.domain.navigation.Screen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

class MainModel extends BaseModel<MainState, Router> implements MainInteractor {

    @NonNull
    private final StringResources stringResources;
    @NonNull
    private final PermissionsSource permissionsSource;
    @NonNull
    private final ScreenBuilder screenBuilder;

    @NonNull
    private final Subject<String> messageBus = PublishSubject.<String>create().toSerialized();

    @Inject
    MainModel(@NonNull Router router, @NonNull StringResources stringResources,
              @NonNull PermissionsSource permissionsSource) {
        super(MainState.DEFAULT, router);
        this.stringResources = stringResources;
        this.permissionsSource = permissionsSource;
        screenBuilder = new ScreenBuilder(stringResources);
    }

    @UiThread
    @Override
    public void firstStart(boolean isRestoring) {
        if (!isRestoring) {
            getRouter().newRootScreen(screenBuilder.build(getState().screen()));
        }
    }

    @UiThread
    @Override
    public void navigateContacts() {
        addDisposable(permissionsSource.requestPermission(false, Manifest.permission.READ_CONTACTS)
                .doOnSuccess(this::checkPermissionState)
                .subscribe());
    }

    @UiThread
    @Override
    public void forceContactsPermissions() {
        getRouter().exit();
        addDisposable(permissionsSource.requestPermission(true, Manifest.permission.READ_CONTACTS)
                .doOnSuccess(this::checkPermissionState)
                .subscribe());
    }

    @UiThread
    @Override
    public void onBack() {
        MainState state = getState();
        List<Screen> backStack = new ArrayList<>(state.backStack());
        if (!backStack.isEmpty()) {
            Screen screen = backStack.get(backStack.size() - 1);
            backStack.remove(backStack.size() - 1);
            setState(MainState.builder()
                    .screen(screen)
                    .backStack(backStack)
                    .build());
        }

        getRouter().exit();
    }

    @Override
    public void navigatePosts() {
        forward(Screen.POSTS);
    }

    @Override
    public void navigateMvi() {
        forward(Screen.MVI);
    }

    @NonNull
    @Override
    public Observable<String> observeSystemMessages() {
        return messageBus;
    }

    @Override
    public void broadcastSystemMessage(@NonNull String message) {
        messageBus.onNext(message);
    }

    private void checkPermissionState(@NonNull PermissionState state) {
        switch (state) {
            case GRANTED:
                forward(Screen.CONTACTS);
                break;
            case SHOW_SETTINGS:
            case NOT_GRANTED:
                broadcastSystemMessage(stringResources.getString(R.string.contacts_permissions_not_granted));
                break;
            case NEED_EXPLANATION:
                forward(Screen.PERMISSION_EXPLANATION);
                break;
            default:
                break;
        }
    }

    private void forward(@NonNull Screen screen) {
        MainState state = getState();
        List<Screen> backStack = new ArrayList<>(state.backStack());
        backStack.add(state.screen());
        setState(MainState.builder()
                .screen(screen)
                .backStack(backStack)
                .build());
        getRouter().navigateTo(screenBuilder.build(getState().screen()));
    }
}
