package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.sample.presentation.sample.SampleInteractor;
import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenter;
import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenterModule;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleView;
import com.a65apps.daggerarchitecturecomponents.presenter.ChildPresenterScope;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterKey;
import com.a65apps.daggerarchitecturecomponents.presenter.ProviderPresenterComponent;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.IntoMap;

@Module(
    subcomponents = ChildPresenterComponent_BindSamplePresenter.SamplePresenterSubcomponent.class
)
public interface ChildPresenterComponent_BindSamplePresenter {
  @Binds
  @IntoMap
  @PresenterKey(SamplePresenter.class)
  PresenterComponentBuilder bindPresenterBuilder(SamplePresenterSubcomponent.Builder builder);

  @ChildPresenterScope
  @Subcomponent(
      modules = {SamplePresenterModule.class}
  )
  interface SamplePresenterSubcomponent extends ProviderPresenterComponent<SampleState, SampleView, SampleInteractor, Router, SamplePresenter> {
    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<SamplePresenterSubcomponent> {
    }
  }
}
