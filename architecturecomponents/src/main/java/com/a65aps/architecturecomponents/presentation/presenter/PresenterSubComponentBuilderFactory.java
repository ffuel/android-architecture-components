package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.component.UiComponentBuilderFactory;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class PresenterSubComponentBuilderFactory
        extends UiComponentBuilderFactory<Class<? extends BasePresenter>,
        PresenterComponentBuilder> {
    @Inject
    public PresenterSubComponentBuilderFactory(@NonNull Map<Class<? extends BasePresenter>,
            Provider<PresenterComponentBuilder>> subComponentBuilders) {
        super(subComponentBuilders);
    }
}
