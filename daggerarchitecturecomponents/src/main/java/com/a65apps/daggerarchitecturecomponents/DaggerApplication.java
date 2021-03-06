package com.a65apps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.daggerarchitecturecomponents.presenter.HasPresenterSubComponentBuilders;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterSubComponentBuilderFactory;

import javax.inject.Inject;

public abstract class DaggerApplication extends dagger.android.DaggerApplication
        implements HasPresenterSubComponentBuilders {

    @Inject
    @Nullable
    PresenterSubComponentBuilderFactory componentFactory;

    @NonNull
    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        if (componentFactory == null) {
            throw new IllegalStateException("Application is not injected, "
                    + "or PresenterInjectionModule is not added to application component");
        }

        return componentFactory.get(presenterClass);
    }
}
