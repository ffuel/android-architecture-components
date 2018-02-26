package com.a65aps.architecturecomponents.data;

import com.a65aps.architecturecomponents.data.log.LoggerDataModule;
import com.a65aps.architecturecomponents.data.resources.ResourcesDataModule;

import dagger.Module;

@Module(includes = {
        LoggerDataModule.class,
        ResourcesDataModule.class
})
public interface DataModule {
}
