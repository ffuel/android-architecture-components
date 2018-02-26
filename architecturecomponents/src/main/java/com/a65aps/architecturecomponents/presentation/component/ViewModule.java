package com.a65aps.architecturecomponents.presentation.component;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import dagger.Binds;
import dagger.Module;

@Module
public interface ViewModule<S extends State, Parcel extends Parcelable,
        StateMapper extends StateToParcelableMapper<S, Parcel>,
        ParcelMapper extends ParcelableToStateMapper<Parcel, S>> {

    @Binds
    @NonNull
    StateToParcelableMapper<S, Parcel> bindStateMapper(@NonNull StateMapper mapper);

    @Binds
    @NonNull
    ParcelableToStateMapper<Parcel, S> bindParcelMapper(@NonNull ParcelMapper mapper);
}
