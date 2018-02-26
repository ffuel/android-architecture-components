package com.a65apps.architecturecomponents.data;

import com.a65apps.architecturecomponents.data.sample.SampleDataModule;

import dagger.Module;

@Module(includes = {
        SampleDataModule.class
})
public interface AppDataModule {
}
