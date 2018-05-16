package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;

import dagger.Module;

@Module
public interface MainPresenterComponent {

    @ContributesPresenterInjector(modules = {MainPresenterModule.class, ChildPresenterComponent.class})
    MainPresenter bindMainPresenter();
}
