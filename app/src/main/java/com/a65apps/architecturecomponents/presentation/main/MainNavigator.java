package com.a65apps.architecturecomponents.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.domain.main.Screen;
import com.a65apps.architecturecomponents.presentation.sample.SampleFragment;
import com.a65aps.architecturecomponents.presentation.activity.ContainerIdProvider;

import ru.terrakok.cicerone.android.SupportAppNavigator;

public class MainNavigator extends SupportAppNavigator {

    public MainNavigator(@NonNull FragmentActivity activity, @NonNull ContainerIdProvider idProvider) {
        super(activity, idProvider.get());
    }

    @Override
    protected Intent createActivityIntent(Context context, String screenKey, Object data) {
        return null;
    }

    @Override
    protected Fragment createFragment(@NonNull String screenKey, @Nullable Object data) {
        Screen screen = Screen.fromString(screenKey);
        if (screen == Screen.SAMPLE) {
            return SampleFragment.newInstance();
        }

        throw new IllegalArgumentException("Unknown screen key: " + screenKey);
    }
}
