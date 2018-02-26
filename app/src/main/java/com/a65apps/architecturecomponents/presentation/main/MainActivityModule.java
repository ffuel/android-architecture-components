package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.presentation.activity.ContainerIdProvider;
import com.a65aps.architecturecomponents.presentation.component.ViewModule;
import com.a65aps.architecturecomponents.presentation.navigation.BaseNavigator;

import dagger.Binds;
import dagger.Module;

@Module
public interface MainActivityModule extends ViewModule<MainState, MainParcelable, MainStateMapper,
        MainParcelMapper> {
    @Binds
    @NonNull
    BaseNavigator bindNavigator(@NonNull MainNavigator navigator);

    @Binds
    @NonNull
    FragmentActivity bindFragmentActivity(@NonNull MainActivity activity);

    @Binds
    @NonNull
    ContainerIdProvider bindContainerId(@NonNull MainContainerIdProvider idProvider);

}
