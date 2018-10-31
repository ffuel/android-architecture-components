package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.IntentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;

import java.util.Map;

public class CiceroneRouter extends ru.terrakok.cicerone.Router implements Router {

    @NonNull
    private final Map<String, FragmentFactory> fragmentMap;
    @NonNull
    private final Map<String, IntentFactory> intentMap;

    public CiceroneRouter(@NonNull Map<String, FragmentFactory> fragmentMap,
                          @NonNull Map<String, IntentFactory> intentMap) {
        this.fragmentMap = fragmentMap;
        this.intentMap = intentMap;
    }

    @Override
    public void navigateTo(@NonNull Screen screen) {
        navigateTo(new CiceroneScreen(fragmentMap, intentMap, screen));
    }

    @Override
    public void newRootScreen(@NonNull Screen screen) {
        newRootScreen(new CiceroneScreen(fragmentMap, intentMap, screen));
    }

    @Override
    public void replaceScreen(@NonNull Screen screen) {
        replaceScreen(new CiceroneScreen(fragmentMap, intentMap, screen));
    }

    @Override
    public void backTo(@NonNull Screen screen) {
        backTo(new CiceroneScreen(fragmentMap, intentMap, screen));
    }

    @Override
    public void newChain(@NonNull Screen... screens) {
        if (screens.length == 0) {
            return;
        }

        CiceroneScreen[] ciceroneScreens = new CiceroneScreen[screens.length];
        for (int i = 0; i < screens.length; i++) {
            ciceroneScreens[i] = new CiceroneScreen(fragmentMap, intentMap, screens[i]);
        }

        newChain(ciceroneScreens);
    }

    @Override
    public void newRootChain(@NonNull Screen... screens) {
        if (screens.length == 0) {
            return;
        }

        CiceroneScreen[] ciceroneScreens = new CiceroneScreen[screens.length];
        for (int i = 0; i < screens.length; i++) {
            ciceroneScreens[i] = new CiceroneScreen(fragmentMap, intentMap, screens[i]);
        }

        newRootChain(ciceroneScreens);
    }

    @Override
    public void navigateTo(@NonNull String key) {
        navigateTo(new BasicScreen(key, null));
    }

    @Override
    public void navigateTo(@NonNull String key, @Nullable Object data) {
        navigateTo(new BasicScreen(key, data));
    }

    @Override
    public void replaceScreen(@NonNull String key) {
        replaceScreen(new BasicScreen(key, null));
    }

    @Override
    public void replaceScreen(@NonNull String key, @Nullable Object data) {
        replaceScreen(new BasicScreen(key, data));
    }

    @Override
    public void backTo(@Nullable String key) {
        backTo(new BasicScreen(key == null ? "" : key, null));
    }

    @Override
    public void newRootScreen(@NonNull String key) {
        newRootScreen(new BasicScreen(key, null));
    }

    @Override
    public void newRootScreen(@NonNull String key, @Nullable Object data) {
        newRootScreen(new BasicScreen(key, data));
    }

    /**
     * @deprecated use {@link #newChain(Screen...)}
     *
     * @param key screen key
     */
    @Override
    @Deprecated
    public void newScreenChain(@NonNull String key) {
//      Deprecated
    }

    /**
     * @deprecated use {@link #newChain(Screen...)}
     *
     * @param key screen key
     */
    @Override
    @Deprecated
    public void newScreenChain(@NonNull String key, @Nullable Object data) {
//      Deprecated
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public void exitWithMessage(@NonNull String message) {
//      Deprecated
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public void showSystemMessage(@NonNull String message) {
//      Deprecated
    }
}
