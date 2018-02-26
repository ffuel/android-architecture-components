package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;

public interface PresenterComponentBuilder<C extends PresenterComponent> {

    @NonNull
    C build();
}
