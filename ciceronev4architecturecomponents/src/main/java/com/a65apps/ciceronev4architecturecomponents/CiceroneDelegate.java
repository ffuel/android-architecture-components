package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.presentation.navigation.NavigatorDelegate;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

@UiThread
public final class CiceroneDelegate implements NavigatorDelegate {

    @NonNull
    private final NavigatorHolder navigatorHolder;
    @NonNull
    private final Navigator navigator;

    public CiceroneDelegate(@NonNull NavigatorHolder navigatorHolder,
                            @NonNull Navigator navigator) {
        this.navigatorHolder = navigatorHolder;
        this.navigator = navigator;
    }

    @Override
    public void onAttach() {
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    public void onDetach() {
        navigatorHolder.removeNavigator();
    }
}
