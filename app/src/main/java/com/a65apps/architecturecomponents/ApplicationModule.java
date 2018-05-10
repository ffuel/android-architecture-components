package com.a65apps.architecturecomponents;

import android.content.Context;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.data.AppDataModule;
import com.a65apps.architecturecomponents.presentation.ActivityBuilder;
import com.a65apps.architecturecomponents.presentation.ComponentsBindings;
import com.a65apps.architecturecomponents.presentation.main.MainNavigationModule;
import com.a65aps.daggerarchitecturecomponents.ConnectionStateModule;
import com.a65aps.daggerarchitecturecomponents.LoggerModule;
import com.a65aps.daggerarchitecturecomponents.PermissionsModule;
import com.a65aps.daggerarchitecturecomponents.ResourcesModule;
import com.a65aps.daggerarchitecturecomponents.SchedulersModule;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjectionModule;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;

@Module(includes = {
        AndroidInjectionModule.class,
        PresenterInjectionModule.class,
        SchedulersModule.class,
        LoggerModule.class,
        ResourcesModule.class,
        ConnectionStateModule.class,
        PermissionsModule.class,
        ComponentsBindings.class,
        ActivityBuilder.class,
        MainNavigationModule.class,
        AppDataModule.class
})
public class ApplicationModule {

    @Provides
    @NonNull
    Context providesContext(@NonNull SampleApplication application) {
        return application;
    }
}
