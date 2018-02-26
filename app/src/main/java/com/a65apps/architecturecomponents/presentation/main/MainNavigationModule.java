package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.navigation.NavigationModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class MainNavigationModule extends NavigationModule<MainRouter> {

    public MainNavigationModule() {
        super(new MainRouter());
    }

    @Provides
    @Singleton
    @NonNull
    Router providesRouter() {
        return getRouter();
    }

    @Provides
    @Singleton
    @NonNull
    MainRouter providesCurrentRouter() {
        return getCurrentRouter();
    }

    @Provides
    @Singleton
    @NonNull
    NavigatorHolder providesNavigatorHolder() {
        return getNavigatorHolder();
    }
}
