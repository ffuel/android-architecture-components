package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

public interface PresenterComponentBuilder<C extends PresenterComponent> {

    @NonNull
    C build();
}
