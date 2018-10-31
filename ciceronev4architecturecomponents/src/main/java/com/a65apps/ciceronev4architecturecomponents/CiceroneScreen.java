package com.a65apps.ciceronev4architecturecomponents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.IntentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;

import java.util.Map;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

final class CiceroneScreen extends SupportAppScreen {

    @NonNull
    private final Map<String, FragmentFactory> fragmentMap;
    @NonNull
    private final Map<String, IntentFactory> intentMap;
    @NonNull
    private final Screen screen;

    CiceroneScreen(@NonNull Map<String, FragmentFactory> fragmentMap,
                   @NonNull Map<String, IntentFactory> intentMap,
                   @NonNull Screen screen) {
        this.fragmentMap = fragmentMap;
        this.intentMap = intentMap;
        this.screen = screen;
    }

    @Override
    @NonNull
    public String getScreenKey() {
        return screen.getScreenKey();
    }

    @Override
    @NonNull
    public Fragment getFragment() {
        FragmentFactory factory = fragmentMap.get(getScreenKey());
        if (factory == null) {
            throw new IllegalArgumentException("Unknown screen key: " + getScreenKey());
        }

        return factory.build(new Bundle(), screen);
    }

    @Override
    @Nullable
    public Intent getActivityIntent(Context context) {
        IntentFactory factory = intentMap.get(getScreenKey());
        if (factory != null) {
            return factory.build(context, new Bundle(), screen);
        }

        return null;
    }
}
