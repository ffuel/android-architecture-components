package com.a65apps.architecturecomponents.sample;

import android.content.Context;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.sample.data.TestAppDataModule;
import com.a65apps.architecturecomponents.sample.presentation.PresenterBuilder;
import com.a65apps.daggerarchitecturecomponents.LoggerModule;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjectionModule;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        PresenterInjectionModule.class,
        TestSchedulersModule.class,
        LoggerModule.class,
        TestResourcesModule.class,
        TestConnectionStateModule.class,
        PermissionsModule.class,
        PresenterBuilder.class,
        TestAppDataModule.class
})
public class TestApplicationModule {

    private final Router router = Mockito.mock(Router.class);
    private final Context context = Mockito.mock(Context.class);

    @Provides
    @Singleton
    @NonNull
    Router providesRouter() {
        return router;
    }

    @Provides
    @Singleton
    @NonNull
    Context providesContext() {
        return context;
    }
}
