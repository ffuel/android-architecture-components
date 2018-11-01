package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PermissionExplanationScreen;
import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationFragment;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;

import java.util.List;

import javax.inject.Inject;

class PermissionsExplanationScreenFactory implements FragmentFactory {

    @Inject
    PermissionsExplanationScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @NonNull Screen screen) {
        if (!(screen instanceof PermissionExplanationScreen)) {
            throw new IllegalArgumentException("Wrong screen type: " + screen.getClass().getName());
        }

        PermissionExplanationScreen permissionsScreen = (PermissionExplanationScreen) screen;

        List<String> list = permissionsScreen.messages();
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        return PermissionsExplanationFragment.newInstance(bundle, list.toArray(new String[list.size()]));
    }
}
