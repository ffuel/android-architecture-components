package com.a65apps.daggerarchitecturecomponents.presenter;

import com.a65apps.architecturecomponents.sample.presentation.main.Presenter;

public interface HasPresenterSubComponentBuilders {

    PresenterComponentBuilder getPresenterSubComponentBuilder(Class<? extends Presenter> presenterClass);
}
