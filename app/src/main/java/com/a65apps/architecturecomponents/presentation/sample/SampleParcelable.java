package com.a65apps.architecturecomponents.presentation.sample;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SampleParcelable implements Parcelable {

    public static SampleParcelable create(SampleState.State state, String text, String data,
                                          String error) {
        return new AutoValue_SampleParcelable(state, text, data, error);
    }

    @NonNull
    public abstract SampleState.State state();

    @NonNull
    public abstract String text();

    @NonNull
    public abstract String data();

    @NonNull
    public abstract String error();
}
