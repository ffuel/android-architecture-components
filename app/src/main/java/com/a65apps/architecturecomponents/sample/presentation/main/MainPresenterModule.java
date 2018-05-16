package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.sample.domain.main.MainDomainModule;

import dagger.Module;

@Module(includes = MainDomainModule.class)
public interface MainPresenterModule {
}
