package com.a65aps.architecturecomponents;

import com.a65aps.architecturecomponents.data.DataModule;
import com.a65aps.architecturecomponents.domain.DomainModule;
import com.a65aps.architecturecomponents.presentation.PresentationModule;

import dagger.Module;

@Module(includes = {
        PresentationModule.class,
        DomainModule.class,
        DataModule.class
})
public interface ArchitectureComponentsModule {
}
