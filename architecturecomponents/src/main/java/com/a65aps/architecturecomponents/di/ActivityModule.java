package com.a65aps.architecturecomponents.di;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65aps.architecturecomponents.presentation.navigation.NavigatorDelegate;

public interface ActivityModule<S extends State, P extends Parcelable,
        SM extends StateToParcelableMapper<S, P>, PM extends ParcelableToStateMapper<P, S>>
        extends ViewModule<S, P, SM, PM> {

    @NonNull
    NavigatorDelegate provideNavigatorDelegate();
}
