package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.argument.ArgumentContainer;

import dagger.BindsInstance;

public interface PresenterComponentBuilder<C extends PresenterComponent> {

    @BindsInstance
    @NonNull
    PresenterComponentBuilder<C> argument(@NonNull ArgumentContainer container);

    @NonNull
    C build();
}
