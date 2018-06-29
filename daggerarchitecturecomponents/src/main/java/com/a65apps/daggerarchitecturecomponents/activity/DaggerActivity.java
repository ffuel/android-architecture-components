package com.a65apps.daggerarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.activity.BaseActivity;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class DaggerActivity<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends BaseActivity<S, Parcel, P>
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
