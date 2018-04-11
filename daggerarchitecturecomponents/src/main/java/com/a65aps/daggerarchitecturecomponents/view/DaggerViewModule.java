package com.a65aps.daggerarchitecturecomponents.view;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.di.ViewModule;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DaggerViewModule<S extends State, P extends Parcelable,
        SM extends StateToParcelableMapper<S, P>, PM extends ParcelableToStateMapper<P, S>> {

    @Provides
    @NonNull
    public final StateToParcelableMapper<S, P> providesStateToParcelableMapper(
            @NonNull ViewModule<S, P, SM, PM> module) {
        return module.provideStateToParcelableMapper();
    }

    @Provides
    @NonNull
    public final ParcelableToStateMapper<P, S> providesParcelableToStateMapper(
            @NonNull ViewModule<S, P, SM, PM> module) {
        return module.provideParcelableToStateMapper();
    }
}
