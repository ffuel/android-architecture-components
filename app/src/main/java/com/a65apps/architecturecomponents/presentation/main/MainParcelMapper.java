package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;

import javax.inject.Inject;

public class MainParcelMapper extends ParcelableToStateMapper<MainParcelable, MainState> {

    @Inject
    public MainParcelMapper() {
        super();
    }

    @NonNull
    @Override
    public MainState map(@NonNull MainParcelable mainParcelable) {
        return MainState.create(mainParcelable.screen());
    }
}
