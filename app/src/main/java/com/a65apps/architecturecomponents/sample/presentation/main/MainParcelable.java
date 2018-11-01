package com.a65apps.architecturecomponents.sample.presentation.main;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.navigation.Screen;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class MainParcelable implements Parcelable {

    public static Builder builder() {
        return new AutoValue_MainParcelable.Builder();
    }

    @NonNull
    public abstract Screen screen();

    @NonNull
    public abstract List<Screen> backStack();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder screen(Screen screen);

        public abstract Builder backStack(List<Screen> backStack);

        public abstract MainParcelable build();
    }
}
