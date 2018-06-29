package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.Multibinds;

@Module
@SuppressWarnings("squid:S1610")
public abstract class PresenterInjectionModule {

    @Multibinds
    @NonNull
    abstract Map<Class<? extends Presenter>, PresenterComponentBuilder>
        presenterInjectorFactories();


    @Binds
    @NonNull
    abstract UiComponentBuilderFactory<Class<? extends Presenter>, PresenterComponentBuilder>
        bindPresenterSubComponentFactory(@NonNull PresenterSubComponentBuilderFactory factory);
}
