package com.a65apps.architecturecomponents.domain.main;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.model.BaseModel;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import javax.inject.Inject;

final class MainModel extends BaseModel<MainState, Router> implements MainInteractor {

    @Inject
    MainModel(@NonNull Router router) {
        super(MainState.create(Screen.SAMPLE), router);

        router.newRootScreen(getState().screen().getName());
    }
}
