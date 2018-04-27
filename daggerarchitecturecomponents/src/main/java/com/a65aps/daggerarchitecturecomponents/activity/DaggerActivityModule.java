package com.a65aps.daggerarchitecturecomponents.activity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.di.ActivityModule;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65aps.architecturecomponents.presentation.navigation.NavigatorDelegate;

import dagger.Module;
import dagger.Provides;

@Module
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
