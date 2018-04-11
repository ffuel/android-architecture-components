package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SampleState implements State {

    public static SampleState create(State state, String text, String data, String error) {
        return new AutoValue_SampleState(state, text, data, error);
    }

    @NonNull
    public abstract State state();

    @NonNull
    public abstract String text();

    @NonNull
    public abstract String data();

    @NonNull
    public abstract String error();

    @NonNull
    public SampleState mutateLoading(@NonNull String text) {
        return SampleState.create(SampleState.State.LOADING, text, data(), error());
    }

    @NonNull
    public SampleState mutateError(@NonNull String message) {
        return SampleState.create(SampleState.State.ERROR, text(), data(), message);
    }

    @NonNull
    public SampleState mutateData(@NonNull String data) {
        return SampleState.create(SampleState.State.COMPLETE, text(), data, error());
    }

    public enum State {
        LOADING,
        COMPLETE,
        ERROR
    }
}
