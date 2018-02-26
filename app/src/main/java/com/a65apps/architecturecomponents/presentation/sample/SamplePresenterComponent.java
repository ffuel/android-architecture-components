package com.a65apps.architecturecomponents.presentation.sample;

import com.a65apps.architecturecomponents.domain.sample.SampleDomainModule;
import com.a65apps.architecturecomponents.domain.sample.SampleInteractor;
import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65apps.architecturecomponents.presentation.main.MainRouter;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterComponentBuilder;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterScope;
import com.a65aps.architecturecomponents.presentation.presenter.ProviderPresenterComponent;

import dagger.Module;
import dagger.Subcomponent;

@PresenterScope
@Subcomponent(modules = {
        SamplePresenterComponent.SamplePresenterModule.class
})
public interface SamplePresenterComponent extends ProviderPresenterComponent<SampleState,
        SampleView, SampleInteractor, MainRouter, SamplePresenter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<SamplePresenterComponent> {
    }

    @Module(includes = {
            SampleDomainModule.class
    })
    interface SamplePresenterModule {

    }
}
