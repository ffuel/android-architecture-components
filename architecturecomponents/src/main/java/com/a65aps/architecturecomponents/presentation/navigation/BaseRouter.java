package com.a65aps.architecturecomponents.presentation.navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.terrakok.cicerone.Router;

public class BaseRouter extends Router {

    public BaseRouter() {
        super();
    }

    public void newRootScreenIfNotExists(@NonNull String screenKey) {
        newRootScreenIfNotExists(screenKey, null);
    }

    public void newRootScreenIfNotExists(@NonNull String screenKey, @Nullable Object data) {
        executeCommand(new NewRootScreenIfNotExistsCommand(screenKey, data));
    }
}
