package com.a65apps.architecturecomponents.presentation.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.presentation.navigation.Screens;
import com.a65apps.architecturecomponents.presentation.sample.SampleFragment;
import com.a65aps.architecturecomponents.presentation.activity.ContainerIdProvider;
import com.a65aps.architecturecomponents.presentation.navigation.BaseNavigator;

import javax.inject.Inject;

public class MainNavigator extends BaseNavigator {

    @Inject
    public MainNavigator(@NonNull FragmentActivity activity, @NonNull ContainerIdProvider idProvider) {
        super(activity, idProvider.get());
    }

    @Override
    protected Intent createActivityIntent(@NonNull String screenKey, @Nullable Object data) {
        return null;
    }

    @Override
    protected Fragment createFragment(@NonNull String screenKey, @Nullable Object data) {
        if (screenKey.equals(Screens.SAMPLE_SCREEN)) {
            return SampleFragment.newInstance();
        }

        throw new IllegalArgumentException("Unknown screen key: " + screenKey);
    }
}
