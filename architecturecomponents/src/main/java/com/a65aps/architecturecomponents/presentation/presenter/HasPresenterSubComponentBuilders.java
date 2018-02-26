package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;

public interface HasPresenterSubComponentBuilders {

    @NonNull
    PresenterComponentBuilder getPresenterSubComponentBuilder(@NonNull Class<? extends BasePresenter> presenterClass);
}
