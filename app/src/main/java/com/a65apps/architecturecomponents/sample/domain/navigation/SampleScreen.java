package com.a65apps.architecturecomponents.sample.domain.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SampleScreen implements Screen {

    public static SampleScreen create() {
        return new AutoValue_SampleScreen();
    }

    @NonNull
    @Override
    public String getScreenKey() {
        return NavigationConstants.SAMPLE_KEY;
    }
}
