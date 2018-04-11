package com.a65aps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

public interface PresenterComponentBuilder<C extends PresenterComponent> {

    @NonNull
    C build();
}
