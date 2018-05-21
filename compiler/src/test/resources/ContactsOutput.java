package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsInteractor;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsPresenter;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsPresenterModule;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsState;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsView;
import com.a65apps.daggerarchitecturecomponents.presenter.ChildPresenterScope;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterKey;
import com.a65apps.daggerarchitecturecomponents.presenter.ProviderPresenterComponent;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.IntoMap;

@Module(
    subcomponents = ChildPresenterComponent_BindContactsPresenter.ContactsPresenterSubcomponent.class
)
public interface ChildPresenterComponent_BindContactsPresenter {
  @Binds
  @IntoMap
  @PresenterKey(ContactsPresenter.class)
  PresenterComponentBuilder bindPresenterBuilder(ContactsPresenterSubcomponent.Builder builder);

  @ChildPresenterScope
  @Subcomponent(
      modules = {ContactsPresenterModule.class}
  )
  interface ContactsPresenterSubcomponent extends ProviderPresenterComponent<ContactsState, ContactsView, ContactsInteractor, Router, ContactsPresenter> {
    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<ContactsPresenterSubcomponent> {
    }
  }
}
