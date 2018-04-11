package com.a65aps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.presentation.presenter.Presenter;

public interface HasPresenterSubComponentBuilders {

    @NonNull
    PresenterComponentBuilder getPresenterSubComponentBuilder(@NonNull Class<? extends Presenter> presenterClass);
}
