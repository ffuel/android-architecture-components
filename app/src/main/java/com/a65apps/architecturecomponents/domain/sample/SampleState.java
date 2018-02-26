package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SampleState implements State {

    public static SampleState create(String text) {
        return new AutoValue_SampleState(text);
    }

    @NonNull
    public abstract String text();
}
