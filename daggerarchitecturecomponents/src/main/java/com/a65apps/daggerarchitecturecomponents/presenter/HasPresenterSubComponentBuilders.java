package com.a65apps.daggerarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

public interface HasPresenterSubComponentBuilders {

    @NonNull
    PresenterComponentBuilder getPresenterSubComponentBuilder(@NonNull Class<? extends Presenter> presenterClass);
}
