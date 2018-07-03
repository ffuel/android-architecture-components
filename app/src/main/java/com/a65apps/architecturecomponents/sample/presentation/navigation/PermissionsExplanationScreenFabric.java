package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.sample.domain.main.Screen;
import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationFragment;
import com.a65apps.ciceronearchitecturecomponents.FragmentFabric;

import javax.inject.Inject;

class PermissionsExplanationScreenFabric implements FragmentFabric {

    @Inject
    PermissionsExplanationScreenFabric() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @Nullable Object data) {
        if (!(data instanceof String[])) {
            throw new IllegalArgumentException("Wrong data for screen " + Screen.PERMISSION_EXPLANATION.getName());
        }
        return PermissionsExplanationFragment.newInstance(bundle, (String[]) data);
    }
}
