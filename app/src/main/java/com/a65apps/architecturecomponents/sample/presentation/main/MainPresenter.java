package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.sample.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterSubComponentBuilderFactory;
import com.a65apps.moxyarchitecturecomponents.presenter.MoxyPresenter;
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
    @CallSuper
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        disposeOnDestroy(getInteractor().observeSystemMessages()
                .observeOn(getUiScheduler())
                .subscribe(getViewState()::showSystemMessage, getLogger()::logError));
    }

    @UiThread
    @Override
    public void onBackPressed() {
        getInteractor().onBack();
    }

    @UiThread
    @NonNull
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }

    @UiThread
    void showContacts() {
        getInteractor().navigateContacts();
    }

    @UiThread
    void showPosts() {
        getInteractor().navigatePosts();
    }

    @UiThread
    void forceContactsPermissions() {
        getInteractor().forceContactsPermissions();
    }

    @UiThread
    void showMvi() {
        getInteractor().navigateMvi();
    }
}
