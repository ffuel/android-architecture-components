package com.a65apps.moxyarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.moxyarchitecturecomponents.R;
import com.arellomobile.mvp.MvpDelegate;

import static org.mockito.Mockito.mock;

public class MoxyTestActivity extends MoxyActivity<State, Parcelable, Presenter> {

    private Presenter presenter = mock(Presenter.class);
    private MvpDelegate mvpDelegate = mock(MvpDelegate.class);

    @Override
    protected void inject() {
    }

    @Override
    protected void updateState(@NonNull Parcelable state) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.test_layout;
    }

    @NonNull
    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @NonNull
    @Override
    public MvpDelegate getMvpDelegate() {
        return mvpDelegate;
    }
}
