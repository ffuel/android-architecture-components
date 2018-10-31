package com.a65apps.architecturecomponents.presentation.navigationv2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BasicScreen implements Screen {

    @NonNull
    private final String key;
    @Nullable
    private final Object data;

    public BasicScreen(@NonNull String key, @Nullable Object data) {
        this.key = key;
        this.data = data;
    }

    @NonNull
    @Override
    public String getScreenKey() {
        return key;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
