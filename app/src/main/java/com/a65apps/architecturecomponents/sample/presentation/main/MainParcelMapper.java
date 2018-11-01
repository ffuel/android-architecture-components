package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;

import javax.inject.Inject;

public class MainParcelMapper extends ParcelableToStateMapper<MainParcelable, MainState> {

    @Inject
    MainParcelMapper() {
        super();
    }

    @NonNull
    @Override
    public MainState map(@NonNull MainParcelable mainParcelable) {
        return MainState.builder()
                .screen(mainParcelable.screen())
                .backStack(mainParcelable.backStack())
                .build();
    }
}
