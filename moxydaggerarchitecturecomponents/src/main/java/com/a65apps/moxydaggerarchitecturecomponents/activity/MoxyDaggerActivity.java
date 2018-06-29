package com.a65apps.moxydaggerarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.moxyarchitecturecomponents.activity.MoxyActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class MoxyDaggerActivity<S extends State, Parcel extends Parcelable, P extends Presenter>
        extends MoxyActivity<S, Parcel, P>
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
