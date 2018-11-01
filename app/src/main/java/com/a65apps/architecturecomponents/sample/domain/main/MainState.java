package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.sample.domain.navigation.Screen;
import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class MainState implements State {

    public static final MainState DEFAULT = MainState.builder()
            .screen(Screen.SAMPLE)
            .backStack(Collections.emptyList())
            .build();

    public static Builder builder() {
        return new AutoValue_MainState.Builder();
    }

    @NonNull
    public abstract Screen screen();

    @NonNull
    public abstract List<Screen> backStack();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder screen(Screen screen);

        public abstract Builder backStack(List<Screen> backStack);

        public abstract MainState build();
    }
}
