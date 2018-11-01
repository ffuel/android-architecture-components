package com.a65apps.architecturecomponents.sample.domain.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MviScreen implements Screen {

    public static MviScreen create() {
        return new AutoValue_MviScreen();
    }

    @NonNull
    @Override
    public String getScreenKey() {
        return NavigationConstants.MVI_KEY;
    }
}
