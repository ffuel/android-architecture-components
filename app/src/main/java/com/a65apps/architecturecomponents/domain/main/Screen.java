package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

public enum Screen {
    SAMPLE("screen_sample");

    @NonNull
    private String name;

    @NonNull
    public static Screen fromString(@NonNull String key) {
        if (SAMPLE.name.equals(key)) {
            return SAMPLE;
        }

        throw new IllegalArgumentException("Unknown screen: " + key);
    }

    Screen(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
