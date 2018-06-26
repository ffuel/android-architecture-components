package com.a65apps.architecturecomponents.di;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

public interface ViewModule<S extends State, P extends Parcelable,
        StateMapper extends StateToParcelableMapper<S, P>,
        ParcelMapper extends ParcelableToStateMapper<P, S>> {

    @NonNull
    StateMapper provideStateToParcelableMapper();

    @NonNull
    ParcelMapper provideParcelableToStateMapper();
}
