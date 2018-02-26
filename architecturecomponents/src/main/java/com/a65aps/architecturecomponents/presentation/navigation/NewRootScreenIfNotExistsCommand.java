package com.a65aps.architecturecomponents.presentation.navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.terrakok.cicerone.commands.Command;

public class NewRootScreenIfNotExistsCommand implements Command {

    @NonNull
    private final String screenKey;
    @Nullable
    private final Object data;

    public NewRootScreenIfNotExistsCommand(@NonNull String screenKey, @Nullable Object data) {
        this.screenKey = screenKey;
        this.data = data;
    }

    @NonNull
    public String getScreenKey() {
        return screenKey;
    }

    @Nullable
    public Object getData() {
        return data;
    }
}
