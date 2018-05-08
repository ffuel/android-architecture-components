package com.a65apps.architecturecomponents.presentation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.contacts.ContactsPresenter;
import com.a65apps.architecturecomponents.presentation.contacts.ContactsPresenterComponent;
import com.a65apps.architecturecomponents.presentation.main.MainPresenter;
import com.a65apps.architecturecomponents.presentation.main.MainPresenterComponent;
import com.a65apps.architecturecomponents.presentation.sample.SamplePresenter;
import com.a65apps.architecturecomponents.presentation.sample.SamplePresenterComponent;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        MainPresenterComponent.class,
        SamplePresenterComponent.class,
        ContactsPresenterComponent.class
})
public interface ComponentsBindings {

    @Binds
    @IntoMap
    @PresenterKey(MainPresenter.class)
    @NonNull
    PresenterComponentBuilder bindsMainComponentBuilder(@NonNull MainPresenterComponent.Builder builder);

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
