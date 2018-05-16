package com.a65apps.architecturecomponents.sample.presentation.sample;

import com.a65apps.architecturecomponents.sample.domain.sample.SampleDomainModule;

import dagger.Module;

@Module(includes = SampleDomainModule.class)
public interface SamplePresenterModule {
}
