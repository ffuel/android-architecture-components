package com.a65aps.moxydaggerarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.architecturecomponents.presentation.view.View;
import com.a65aps.moxyarchitecturecomponents.activity.MoxyActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class MoxyDaggerActivity<S extends State, Parcel extends Parcelable,
        V extends View<S>, I extends Interactor<S, R>, R extends Router,
        P extends Presenter<S, V, I, R>> extends MoxyActivity<S, Parcel, V, I, R, P>
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
