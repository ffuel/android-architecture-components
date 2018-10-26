package com.a65apps.architecturecomponents.sample.domain.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.StateProvider;

import javax.inject.Inject;

class DefaultMviStateProvider implements StateProvider<MviState> {

    @Inject
    DefaultMviStateProvider() {
//      Inject constructor
    }

    @NonNull
    @Override
    public MviState provide() {
        return MviState.DEFAULT;
    }
}
