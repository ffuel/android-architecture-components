package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.presentation.mvi.MviFragment;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;

import javax.inject.Inject;

class MviScreenFactory implements FragmentFactory {

    @Inject
    MviScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @NonNull Screen screen) {
        return MviFragment.newInstance();
    }
}
