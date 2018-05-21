package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;

import dagger.Module;

@Module
interface MainPresenterComponent {

    @ContributesPresenterInjector(modules = {MainPresenterModule.class})
    MainPresenter bindMainPresenter();
}
