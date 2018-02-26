package com.a65apps.architecturecomponents.domain.main;

import com.a65aps.architecturecomponents.domain.model.BaseModel;

import javax.inject.Inject;

public final class MainModel extends BaseModel<MainState> implements MainInteractor {

    @Inject
    public MainModel() {
        setState(MainState.create(Screen.SAMPLE));
    }
}
