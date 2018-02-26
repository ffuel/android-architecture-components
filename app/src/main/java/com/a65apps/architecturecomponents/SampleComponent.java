package com.a65apps.architecturecomponents;

import com.a65apps.architecturecomponents.data.AppDataModule;
import com.a65apps.architecturecomponents.presentation.ActivityBuilder;
import com.a65apps.architecturecomponents.presentation.ComponentsBindings;
import com.a65apps.architecturecomponents.presentation.main.MainNavigationModule;
import com.a65aps.architecturecomponents.ApplicationDelegateComponent;
import com.a65aps.architecturecomponents.ArchitectureComponentsModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ArchitectureComponentsModule.class,
        ComponentsBindings.class,
        ActivityBuilder.class,
        MainNavigationModule.class,
        AppDataModule.class
})
@Singleton
public interface SampleComponent extends ApplicationDelegateComponent<SampleApplicationDelegate> {

    @Component.Builder
    interface Builder extends ApplicationDelegateComponent.Builder<SampleComponent> {
    }
}
