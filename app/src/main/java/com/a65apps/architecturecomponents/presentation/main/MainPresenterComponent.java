package com.a65apps.architecturecomponents.presentation.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.main.MainDomainModule;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.main.MainState;
import com.a65apps.architecturecomponents.presentation.contacts.ContactsPresenter;
import com.a65apps.architecturecomponents.presentation.contacts.ContactsPresenterComponent;
import com.a65apps.architecturecomponents.presentation.sample.SamplePresenter;
import com.a65apps.architecturecomponents.presentation.sample.SamplePresenterComponent;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterKey;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterScope;
import com.a65aps.daggerarchitecturecomponents.presenter.ProviderPresenterComponent;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.IntoMap;

@PresenterScope
@Subcomponent(modules = {
        MainPresenterComponent.MainPresenterModule.class
})
public interface MainPresenterComponent extends ProviderPresenterComponent<MainState, MainView,
        MainInteractor, Router, MainPresenter> {

    @Subcomponent.Builder
    interface Builder extends PresenterComponentBuilder<MainPresenterComponent> {
    }

    @Module(includes = {
            MainDomainModule.class
    }, subcomponents = {
            SamplePresenterComponent.class,
            ContactsPresenterComponent.class
    })
    interface MainPresenterModule {

        @Binds
        @IntoMap
        @PresenterKey(SamplePresenter.class)
        @NonNull
        PresenterComponentBuilder bindsSampleComponentBuilder(@NonNull SamplePresenterComponent.Builder builder);

        @Binds
        @IntoMap
        @PresenterKey(ContactsPresenter.class)
        @NonNull
        PresenterComponentBuilder bindsContactsComponentBuilder(@NonNull ContactsPresenterComponent.Builder builder);
    }
}
