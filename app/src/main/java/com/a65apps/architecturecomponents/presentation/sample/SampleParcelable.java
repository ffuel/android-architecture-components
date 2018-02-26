package com.a65apps.architecturecomponents.presentation.sample;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SampleParcelable implements Parcelable {

    public static SampleParcelable create(String text) {
        return new AutoValue_SampleParcelable(text);
    }

    @NonNull
    public abstract String text();
}
