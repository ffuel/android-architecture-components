package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.presentation.activity.BaseActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

public class MainActivity extends BaseActivity<MainState, MainParcelable, MainView, MainInteractor,
        MainRouter, MainPresenter> implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void updateState(@NonNull MainParcelable state) {
        presenter.updateScreen(state.screen());
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
        return providePresenter(MainPresenter.class);
    }
}
