package com.a65apps.architecturecomponents.sample.domain.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MviState implements State {

    static final MviState DEFAULT = MviState.builder()
            .title("")
            .subtitle("")
            .build();

    public static Builder builder() {
        return new AutoValue_MviState.Builder();
    }

    @NonNull
    public abstract String title();

    @NonNull
    public abstract String subtitle();

    @NonNull
    MviState mutateTitle(@NonNull String title) {
        return MviState.builder()
                .title(title)
                .subtitle(subtitle())
                .build();
    }

    @NonNull
    MviState mutateSubtitle(@NonNull String subtitle) {
        return MviState.builder()
                .title(title())
                .subtitle(subtitle)
                .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder subtitle(String subtitle);

        public abstract MviState build();
    }
}
