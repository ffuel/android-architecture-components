package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65apps.architecturecomponents.presentation.common.ButterActivity;
import com.a65apps.architecturecomponents.presentation.contacts.SearchContactsListener;
import com.a65apps.architecturecomponents.presentation.permissions.PermissionsExplanationListener;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

public class MainActivity extends ButterActivity<MainState, MainParcelable, MainView, MainInteractor,
        Router, MainPresenter> implements MainView, SearchContactsListener, PermissionsExplanationListener {

    @InjectPresenter
    MainPresenter presenter;

    @Inject
    @Nullable
    PermissionsSource permissionsSource;

    @Override
    protected void updateState(@NonNull MainParcelable state) {
        setTitle(state.screen().getTitle());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected MainPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    MainPresenter provideMainPresenter() {
        return PresenterInjector.build(MainPresenter.class, getApplication());
    }

    @Override
    public void onSearchContacts() {
        if (permissionsSource == null) {
            return;
        }

        presenter.showContacts(permissionsSource);
    }

    @Override
    public void onUserAgree() {
        if (permissionsSource == null) {
            return;
        }

        presenter.forceContactsPermissions(permissionsSource);
    }
}
