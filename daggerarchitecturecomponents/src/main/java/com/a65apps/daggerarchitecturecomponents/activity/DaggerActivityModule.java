package com.a65apps.daggerarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.di.ActivityModule;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.presentation.navigation.NavigatorDelegate;

import dagger.Module;
import dagger.Provides;

@Module
@SuppressWarnings("squid:S1610")
public abstract class DaggerActivityModule<S extends State, P extends Parcelable,
        SM extends StateToParcelableMapper<S, P>, PM extends ParcelableToStateMapper<P, S>> {

    @Provides
    @NonNull
    public final StateToParcelableMapper<S, P> providesStateToParcelableMapper(
            @NonNull ActivityModule<S, P, SM, PM> module) {
        return module.provideStateToParcelableMapper();
    }

    @Provides
    @NonNull
    public final ParcelableToStateMapper<P, S> providesParcelableToStateMapper(
            @NonNull ActivityModule<S, P, SM, PM> module) {
        return module.provideParcelableToStateMapper();
    }

    @Provides
    @NonNull
    public final NavigatorDelegate providesNavigatorDelegate(
            @NonNull ActivityModule<S, P, SM, PM> module) {
        return module.provideNavigatorDelegate();
    }
}
