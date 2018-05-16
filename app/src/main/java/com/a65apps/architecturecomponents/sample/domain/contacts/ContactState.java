package com.a65apps.architecturecomponents.sample.domain.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

import net.jcip.annotations.ThreadSafe;

@AutoValue
@ThreadSafe
public abstract class ContactState implements State {

    public static Builder builder() {
        return new AutoValue_ContactState.Builder();
    }

    @NonNull
    public abstract String name();

    @NonNull
    public abstract String photo();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder photo(String photo);

        public abstract ContactState build();
    }
}
