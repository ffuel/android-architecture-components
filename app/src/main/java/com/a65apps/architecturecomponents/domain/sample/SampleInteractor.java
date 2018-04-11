package com.a65apps.architecturecomponents.domain.sample;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

public interface SampleInteractor extends Interactor<SampleState, Router> {

    void reload();
}
