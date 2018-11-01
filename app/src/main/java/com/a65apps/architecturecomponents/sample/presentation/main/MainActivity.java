package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.sample.presentation.common.ButterActivity;
import com.a65apps.architecturecomponents.sample.presentation.contacts.SearchContactsListener;
import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationListener;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.daggerarchitecturecomponents.presenter.HasPresenterSubComponentBuilders;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;

public class MainActivity extends ButterActivity<MainState, MainParcelable, MainPresenter>
        implements MainView, SearchContactsListener, PermissionsExplanationListener,
        HasPresenterSubComponentBuilders {

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.container)
    View container;

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
        presenter.showContacts();
    }

    @Override
    public void onShowPosts() {
        presenter.showPosts();
    }

    @Override
    public void onShowMvi() {
        presenter.showMvi();
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

    @Override
    public void showSystemMessage(@NonNull String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
    }
}
