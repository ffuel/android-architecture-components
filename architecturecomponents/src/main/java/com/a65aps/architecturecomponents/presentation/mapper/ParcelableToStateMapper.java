package com.a65aps.architecturecomponents.presentation.mapper;

import android.os.Parcelable;

import com.a65aps.architecturecomponents.domain.Mapper;
import com.a65aps.architecturecomponents.domain.State;

public abstract class ParcelableToStateMapper<From extends Parcelable, To extends State>
        extends Mapper<From, To> {

    public ParcelableToStateMapper() {
        // Empty constructor
    }
}
