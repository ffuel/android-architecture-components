package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.domain.navigation.SampleScreen;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleFragment;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;

import javax.inject.Inject;

class SampleScreenFactory implements FragmentFactory {

    @Inject
    SampleScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @NonNull Screen screen) {
        if (!(screen instanceof SampleScreen)) {
            throw new IllegalArgumentException("Wrong screen type: " + screen.getClass().getName());
        }

        return SampleFragment.newInstance();
    }
}
