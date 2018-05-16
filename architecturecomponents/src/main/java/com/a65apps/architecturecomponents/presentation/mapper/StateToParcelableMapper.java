package com.a65apps.architecturecomponents.presentation.mapper;

import android.os.Parcelable;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.domain.State;

public abstract class StateToParcelableMapper<From extends State, To extends Parcelable>
        extends Mapper<From, To> {

    public StateToParcelableMapper() {
        // Empty constructor
    }
}
