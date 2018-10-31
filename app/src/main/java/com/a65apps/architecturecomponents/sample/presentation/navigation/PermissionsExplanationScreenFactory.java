package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationFragment;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;

import javax.inject.Inject;

class PermissionsExplanationScreenFactory implements FragmentFactory {

    @Inject
    PermissionsExplanationScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @NonNull Screen screen) {
        if (!(screen instanceof BasicScreen)) {
            throw new IllegalArgumentException("Wrong screen type");
        }

        BasicScreen basicScreen = (BasicScreen) screen;
        Object data = basicScreen.getData();
        if (!(data instanceof String[])) {
            throw new IllegalArgumentException("Wrong data for screen " + basicScreen.getScreenKey());
        }

        return PermissionsExplanationFragment.newInstance(bundle, (String[]) data);
    }
}
