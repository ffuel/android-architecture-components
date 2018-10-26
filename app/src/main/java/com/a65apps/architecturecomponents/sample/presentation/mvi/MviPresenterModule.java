package com.a65apps.architecturecomponents.sample.presentation.mvi;

import com.a65apps.architecturecomponents.sample.domain.mvi.MviDomainModule;

import dagger.Module;

@Module(includes = MviDomainModule.class)
public interface MviPresenterModule {
}
