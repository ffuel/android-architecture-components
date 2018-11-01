package com.a65apps.architecturecomponents.sample.domain.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class PermissionExplanationScreen implements Screen {

    public static PermissionExplanationScreen create(List<String> messages) {
        return new AutoValue_PermissionExplanationScreen(messages);
    }

    @NonNull
    public abstract List<String> messages();

    @NonNull
    @Override
    public String getScreenKey() {
        return NavigationConstants.PERMISSION_EXPLANATION_KEY;
    }
}
