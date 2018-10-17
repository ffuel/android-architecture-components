package com.a65apps.architecturecomponents.domain.argument;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

public class ArgumentContainer {

    @Nullable
    private final Object argument;

    public ArgumentContainer(@Nullable Object argument) {
        this.argument = argument;
    }

    @NonNull
    public <T> T argument(@NonNull Class<T> type) {
        return Objects.requireNonNull(type.cast(argument));
    }
}
