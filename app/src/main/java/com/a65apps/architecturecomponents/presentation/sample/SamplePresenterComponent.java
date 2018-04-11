package com.a65apps.architecturecomponents.presentation.sample;

import com.a65apps.architecturecomponents.domain.sample.SampleDomainModule;
import com.a65apps.architecturecomponents.domain.sample.SampleInteractor;
import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterScope;
import com.a65aps.daggerarchitecturecomponents.presenter.ProviderPresenterComponent;

import dagger.Module;
import dagger.Subcomponent;

@PresenterScope
@Subcomponent(modules = {
        SamplePresenterComponent.SamplePresenterModule.class
})
public interface SamplePresenterComponent extends ProviderPresenterComponent<SampleState,
        SampleView, SampleInteractor, Router, SamplePresenter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<SamplePresenterComponent> {
    }

    @Module(includes = {
            SampleDomainModule.class
    })
    interface SamplePresenterModule {

    }
}
