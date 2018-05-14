package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65apps.architecturecomponents.presentation.common.ButterActivity;
import com.a65apps.architecturecomponents.presentation.contacts.SearchContactsListener;
import com.a65apps.architecturecomponents.presentation.permissions.PermissionsExplanationListener;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.daggerarchitecturecomponents.presenter.HasPresenterSubComponentBuilders;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

public class MainActivity extends ButterActivity<MainState, MainParcelable, MainView, MainInteractor,
        Router, MainPresenter> implements MainView, SearchContactsListener, PermissionsExplanationListener,
        HasPresenterSubComponentBuilders {

    @InjectPresenter
    MainPresenter presenter;

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
        return PresenterInjector.build(MainPresenter.class, this);
    }

    @Override
    public void onSearchContacts() {
        presenter.showContacts();
    }

    @Override
    public void onUserAgree() {
        presenter.forceContactsPermissions();
    }

    @NonNull
    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return presenter.getPresenterSubComponentBuilder(presenterClass);
    }
}
