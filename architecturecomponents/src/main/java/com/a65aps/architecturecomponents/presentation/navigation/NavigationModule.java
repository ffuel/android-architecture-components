package com.a65aps.architecturecomponents.presentation.navigation;

import android.support.annotation.NonNull;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;

public class NavigationModule<R extends BaseRouter> {
    @NonNull
    private final Cicerone<R> cicerone;

    public NavigationModule(@NonNull R router) {
        cicerone = Cicerone.create(router);
    }

    @NonNull
    protected BaseRouter getRouter() {
        return cicerone.getRouter();
    }

    @NonNull
    protected R getCurrentRouter() {
        return cicerone.getRouter();
    }

    @NonNull
    protected NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }
}
