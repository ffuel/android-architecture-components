package com.a65aps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.presenter.Presenter;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.Multibinds;

@Module
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
