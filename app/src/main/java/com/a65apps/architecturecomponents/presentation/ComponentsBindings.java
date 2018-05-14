package com.a65apps.architecturecomponents.presentation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.main.MainPresenter;
import com.a65apps.architecturecomponents.presentation.main.MainPresenterComponent;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        MainPresenterComponent.class
})
public interface ComponentsBindings {

    @Binds
    @IntoMap
    @PresenterKey(MainPresenter.class)
    @NonNull
    PresenterComponentBuilder bindsMainComponentBuilder(@NonNull MainPresenterComponent.Builder builder);
}
