package com.a65apps.architecturecomponents.sample;

import android.content.Context;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.data.AppDataModule;
import com.a65apps.architecturecomponents.sample.presentation.ActivityBuilder;
import com.a65apps.architecturecomponents.sample.presentation.PresenterBuilder;
import com.a65apps.architecturecomponents.sample.presentation.main.MainNavigationModule;
import com.a65apps.architecturecomponents.sample.presentation.navigation.FragmentNavigationModule;
import com.a65apps.architecturecomponents.sample.presentation.navigation.IntentNavigationModule;
import com.a65apps.daggerarchitecturecomponents.ConnectionStateModule;
import com.a65apps.daggerarchitecturecomponents.LoggerModule;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;
import com.a65apps.daggerarchitecturecomponents.ResourcesModule;
import com.a65apps.daggerarchitecturecomponents.SchedulersModule;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjectionModule;

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
        PresenterBuilder.class,
        ActivityBuilder.class,
        MainNavigationModule.class,
        FragmentNavigationModule.class,
        IntentNavigationModule.class,
        AppDataModule.class
})
public class ApplicationModule {

    @Provides
    @NonNull
    Context providesContext(@NonNull SampleApplication application) {
        return application;
    }
}
