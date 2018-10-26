package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.sample.presentation.mvi.MviFragment;
import com.a65apps.ciceronearchitecturecomponents.FragmentFactory;

import javax.inject.Inject;

class MviScreenFactory implements FragmentFactory {

    @Inject
    MviScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @Nullable Object data) {
        return MviFragment.newInstance();
    }
}
