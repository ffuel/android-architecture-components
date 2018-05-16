package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.ciceronearchitecturecomponents.CiceroneRouter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;

@Module
public class MainNavigationModule {

    private final Cicerone<CiceroneRouter> router = Cicerone.create(new CiceroneRouter());

    @Provides
    @Singleton
    @NonNull
    Router providesRouter() {
        return router.getRouter();
    }

    @Provides
    @Singleton
    @NonNull
    NavigatorHolder providesNavigatorHolder() {
        return router.getNavigatorHolder();
    }
}
