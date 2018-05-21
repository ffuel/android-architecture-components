package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterKey;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterScope;
import com.a65apps.daggerarchitecturecomponents.presenter.ProviderPresenterComponent;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.IntoMap;

@Module(
    subcomponents = MainPresenterComponent_BindMainPresenter.MainPresenterSubcomponent.class
)
public interface MainPresenterComponent_BindMainPresenter {
  @Binds
  @IntoMap
  @PresenterKey(MainPresenter.class)
  PresenterComponentBuilder bindPresenterBuilder(MainPresenterSubcomponent.Builder builder);

  @PresenterScope
  @Subcomponent(
      modules = {MainPresenterModule.class, }
  )
  interface MainPresenterSubcomponent extends ProviderPresenterComponent<MainState, MainView, MainInteractor, Router, MainPresenter> {
    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<MainPresenterSubcomponent> {
    }
  }
}
