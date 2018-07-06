package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.di.ActivityModule;
import com.a65apps.architecturecomponents.presentation.navigation.NavigatorDelegate;
import com.a65apps.ciceronearchitecturecomponents.CiceroneDelegate;
import com.a65apps.ciceronearchitecturecomponents.FragmentFabric;
import com.a65apps.ciceronearchitecturecomponents.IntentFabric;
import com.a65apps.ciceronearchitecturecomponents.NavigationInterceptor;
import com.a65apps.daggerarchitecturecomponents.activity.DaggerActivityModule;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.NavigatorHolder;

@Module
public class MainActivityModule extends DaggerActivityModule<MainState, MainParcelable,
        MainStateMapper, MainParcelMapper> {

    @Provides
    @NonNull
    FragmentActivity providesFragmentActivity(@NonNull MainActivity activity) {
        return activity;
    }

    @Provides
    @NonNull
    ActivityModule<MainState, MainParcelable, MainStateMapper,
                MainParcelMapper> providesModule(@NonNull FragmentActivity activity,
                                                 @NonNull NavigatorHolder holder,
                                                 @NonNull Map<String, FragmentFabric> fragmentMap,
                                                 @NonNull Map<String, IntentFabric> intentMap,
                                                 @NonNull Map<String, NavigationInterceptor> interceptorMap) {
        return new ActivityModule<MainState, MainParcelable, MainStateMapper,
                MainParcelMapper>() {

            @NonNull
            @Override
            public MainStateMapper provideStateToParcelableMapper() {
                return new MainStateMapper();
            }

            @NonNull
            @Override
            public MainParcelMapper provideParcelableToStateMapper() {
                return new MainParcelMapper();
            }

            @NonNull
            @Override
            public NavigatorDelegate provideNavigatorDelegate() {
                return new CiceroneDelegate(holder, new MainNavigator(activity, new MainContainerIdProvider(),
                        fragmentMap, intentMap, interceptorMap));
            }
        };
    }
}
