package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.di.ActivityModule;
import com.a65aps.architecturecomponents.domain.permissions.RequestPermissionsManager;
import com.a65aps.architecturecomponents.presentation.navigation.NavigatorDelegate;
import com.a65aps.ciceronearchitecturecomponents.CiceroneDelegate;
import com.a65aps.daggerarchitecturecomponents.PermissionsModule;
import com.a65aps.daggerarchitecturecomponents.activity.DaggerActivityModule;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.NavigatorHolder;

@Module(includes = {
        PermissionsModule.class
})
public class MainActivityModule extends DaggerActivityModule<MainState, MainParcelable,
        MainStateMapper, MainParcelMapper> {

    @Provides
    @NonNull
    FragmentActivity providesFragmentActivity(@NonNull MainActivity activity) {
        return activity;
    }

    @Provides
    @NonNull
    RequestPermissionsManager providesPermissionsManager(@NonNull MainActivity activity) {
        return activity;
    }

    @Provides
    @NonNull
    ActivityModule<MainState, MainParcelable, MainStateMapper,
                MainParcelMapper> providesModule(@NonNull FragmentActivity activity,
                                                 @NonNull NavigatorHolder holder) {
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
                return new CiceroneDelegate(holder, new MainNavigator(activity, new MainContainerIdProvider()));
            }
        };
    }
}
