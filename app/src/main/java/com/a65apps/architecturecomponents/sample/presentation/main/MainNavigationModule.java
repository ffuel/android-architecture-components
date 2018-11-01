package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.IntentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.ciceronev4architecturecomponents.CiceroneRouter;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;

@Module
public class MainNavigationModule {

    @Provides
    @Singleton
    @NonNull
    Cicerone<CiceroneRouter> providesCicerone(@NonNull Map<String, FragmentFactory> fragmentMap,
                                              @NonNull Map<String, IntentFactory> intentMap) {
        return Cicerone.create(new CiceroneRouter(fragmentMap, intentMap));
    }

    @Provides
    @Singleton
    @NonNull
    Router providesRouter(@NonNull Cicerone<CiceroneRouter> router) {
        return router.getRouter();
    }

    @Provides
    @Singleton
    @NonNull
    NavigatorHolder providesNavigatorHolder(@NonNull Cicerone<CiceroneRouter> router) {
        return router.getNavigatorHolder();
    }
}
