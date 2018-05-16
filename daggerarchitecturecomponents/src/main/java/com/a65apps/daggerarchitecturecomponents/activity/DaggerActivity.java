package com.a65apps.daggerarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.activity.BaseActivity;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class DaggerActivity<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> extends BaseActivity<S, Parcel, V, I, R, P>
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @CallSuper
    @Override
    protected void inject() {
        AndroidInjection.inject(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
