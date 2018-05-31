package com.a65apps.architecturecomponents.sample.domain.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PostState implements State {

    public static Builder builder() {
        return new AutoValue_PostState.Builder();
    }

    @NonNull
    public abstract String name();

    @NonNull
    public abstract String displayName();

    @NonNull
    public abstract String shortDescription();

    @NonNull
    public abstract String description();

    @NonNull
    public abstract String createdBy();

    public abstract double score();

    public abstract boolean featured();

    public abstract boolean curated();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder displayName(String displayName);

        public abstract Builder shortDescription(String shortDescription);

        public abstract Builder description(String description);

        public abstract Builder createdBy(String createdBy);

        public abstract Builder score(double score);

        public abstract Builder featured(boolean featured);

        public abstract Builder curated(boolean curated);

        public abstract PostState build();
    }
}
