package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MainState implements State {

    public static MainState create(Screen screen) {
        return new AutoValue_MainState(screen);
    }

    @NonNull
    public abstract Screen screen();
}
