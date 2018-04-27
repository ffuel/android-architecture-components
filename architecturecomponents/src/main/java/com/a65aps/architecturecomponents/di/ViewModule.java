package com.a65aps.architecturecomponents.di;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

public interface ViewModule<S extends State, P extends Parcelable,
        SM extends StateToParcelableMapper<S, P>, PM extends ParcelableToStateMapper<P, S>> {

    @NonNull
    SM provideStateToParcelableMapper();

    @NonNull
    PM provideParcelableToStateMapper();
}
