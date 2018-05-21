package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.sample.presentation.main.Presenter;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.Multibinds;

@Module
public abstract class PresenterInjectionModule {

    @Multibinds
    abstract Map<Class<? extends Presenter>, PresenterComponentBuilder>
        presenterInjectorFactories();


    @Binds
    abstract UiComponentBuilderFactory<Class<? extends Presenter>, PresenterComponentBuilder>
        bindPresenterSubComponentFactory(PresenterSubComponentBuilderFactory factory);
}
