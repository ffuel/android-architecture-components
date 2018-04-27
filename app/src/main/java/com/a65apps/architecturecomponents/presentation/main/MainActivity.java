package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65apps.architecturecomponents.presentation.common.ButterActivity;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

public class MainActivity extends ButterActivity<MainState, MainParcelable, MainView, MainInteractor,
        Router, MainPresenter> implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void updateState(@NonNull MainParcelable state) {
        // Empty body
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
}
