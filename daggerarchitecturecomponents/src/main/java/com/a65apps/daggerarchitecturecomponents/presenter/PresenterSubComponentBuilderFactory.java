package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresenterSubComponentBuilderFactory
        extends UiComponentBuilderFactory<Class<? extends Presenter>, PresenterComponentBuilder> {

    @Inject
    public PresenterSubComponentBuilderFactory(@NonNull Map<Class<? extends Presenter>,
            Provider<PresenterComponentBuilder>> subComponentBuilders) {
        super(subComponentBuilders);
    }
}
