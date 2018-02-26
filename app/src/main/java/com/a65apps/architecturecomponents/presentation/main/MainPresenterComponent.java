package com.a65apps.architecturecomponents.presentation.main;

import com.a65apps.architecturecomponents.domain.main.MainDomainModule;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterComponentBuilder;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterScope;
import com.a65aps.architecturecomponents.presentation.presenter.ProviderPresenterComponent;

import dagger.Module;
import dagger.Subcomponent;

@PresenterScope
@Subcomponent(modules = {
        MainPresenterComponent.MainPresenterModule.class
})
public interface MainPresenterComponent extends ProviderPresenterComponent<MainState, MainView,
        MainInteractor, MainRouter, MainPresenter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<MainPresenterComponent> {
    }

    @Module(includes = {
            MainDomainModule.class
    })
    interface MainPresenterModule {

    }
}
