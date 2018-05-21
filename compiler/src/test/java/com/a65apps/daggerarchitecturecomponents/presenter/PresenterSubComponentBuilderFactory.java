package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.sample.presentation.main.Presenter;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresenterSubComponentBuilderFactory
        extends UiComponentBuilderFactory<Class<? extends Presenter>, PresenterComponentBuilder> {

    @Inject
    public PresenterSubComponentBuilderFactory(Map<Class<? extends Presenter>,
            Provider<PresenterComponentBuilder>> subComponentBuilders) {
        super(subComponentBuilders);
    }
}
