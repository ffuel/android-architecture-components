package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class MviParcelable implements Parcelable {

    public static Builder builder() {
        return new AutoValue_MviParcelable.Builder();
    }

    @NonNull
    public abstract String title();

    @NonNull
    public abstract String subtitle();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder subtitle(String subtitle);

        public abstract MviParcelable build();
    }
}
