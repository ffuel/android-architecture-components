package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import javax.inject.Inject;

public class MainStateMapper extends StateToParcelableMapper<MainState, MainParcelable> {

    @Inject
    public MainStateMapper() {
        super();
    }

    @NonNull
    @Override
    public MainParcelable map(@NonNull MainState mainState) {
        return MainParcelable.create(mainState.screen());
    }
}
