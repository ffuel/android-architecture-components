package com.a65apps.architecturecomponents.presentation.contacts;

import com.a65apps.architecturecomponents.domain.contacts.ContactsDomainModule;
import com.a65apps.architecturecomponents.domain.contacts.ContactsInteractor;
import com.a65apps.architecturecomponents.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.domain.contacts.ContactsState;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.ChildPresenterScope;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.ProviderPresenterComponent;
import com.a65aps.moxyarchitecturecomponents.view.MoxyCompositeStateView;

import dagger.Module;
import dagger.Subcomponent;

@ChildPresenterScope
@Subcomponent(modules = {
        ContactsPresenterComponent.ContactsPresenterModule.class
})
public interface ContactsPresenterComponent extends ProviderPresenterComponent<ContactsState,
        MoxyCompositeStateView<ContactsState, ContactsListState>, ContactsInteractor, Router, ContactsPresenter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<ContactsPresenterComponent> {
    }

    @Module(includes = {
            ContactsDomainModule.class
    })
    interface ContactsPresenterModule {

    }
}
