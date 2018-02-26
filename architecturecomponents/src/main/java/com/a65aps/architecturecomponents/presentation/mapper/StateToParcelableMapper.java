package com.a65aps.architecturecomponents.presentation.mapper;

import android.os.Parcelable;

import com.a65aps.architecturecomponents.domain.Mapper;
import com.a65aps.architecturecomponents.domain.State;

public abstract class StateToParcelableMapper<From extends State, To extends Parcelable>
        extends Mapper<From, To> {

    public StateToParcelableMapper() {
        // Empty constructor
    }
}
