package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import javax.inject.Inject;

public class MainStateMapper extends StateToParcelableMapper<MainState, MainParcelable> {

    @Inject
    MainStateMapper() {
        super();
    }

    @NonNull
    @Override
    public MainParcelable map(@NonNull MainState mainState) {
        return MainParcelable.builder()
                .screen(mainState.screen())
                .backStack(mainState.backStack())
                .build();
    }
}
