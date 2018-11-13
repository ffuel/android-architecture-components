package com.a65apps.architecturecomponents.sample;

import android.content.Context;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.data.TestAppDataModule;
import com.a65apps.architecturecomponents.sample.presentation.ActivityBuilder;
import com.a65apps.architecturecomponents.sample.presentation.PresenterBuilder;
import com.a65apps.architecturecomponents.sample.presentation.main.MainNavigationModule;
import com.a65apps.architecturecomponents.sample.presentation.navigation.AnimationNavigationModule;
import com.a65apps.architecturecomponents.sample.presentation.navigation.FragmentNavigationModule;
import com.a65apps.architecturecomponents.sample.presentation.navigation.IntentNavigationModule;
import com.a65apps.architecturecomponents.sample.presentation.navigation.InterceptorNavigationModule;
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
        TestConnectionStateModule.class,
        PermissionsModule.class,
        PresenterBuilder.class,
        ActivityBuilder.class,
        MainNavigationModule.class,
        FragmentNavigationModule.class,
        InterceptorNavigationModule.class,
        IntentNavigationModule.class,
        AnimationNavigationModule.class,
        TestAppDataModule.class
})
public class TestSampleModule {

    @Provides
    @NonNull
    Context providesContext(@NonNull TestsApp application) {
        return application;
    }
}
