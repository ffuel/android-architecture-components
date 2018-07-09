package com.a65apps.architecturecomponents.sample.presentation.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.fragment.FragmentDelegate;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.di.ViewModule;
import com.a65apps.daggerarchitecturecomponents.view.DaggerViewModule;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsFragmentModule extends DaggerViewModule<ContactsState, ContactsParcelable,
        ContactsStateMapper, ContactsParcelMapper> {

    @Provides
    @NonNull
    ViewModule<ContactsState, ContactsParcelable, ContactsStateMapper,
            ContactsParcelMapper> providesModule() {
        return new ViewModule<ContactsState, ContactsParcelable, ContactsStateMapper,
                ContactsParcelMapper>() {
            @NonNull
            @Override
            public ContactsStateMapper provideStateToParcelableMapper() {
                return new ContactsStateMapper();
            }

            @NonNull
            @Override
            public ContactsParcelMapper provideParcelableToStateMapper() {
                return new ContactsParcelMapper();
            }
        };
    }

    @Provides
    @NonNull
    FragmentDelegate.FragmentInterface<ContactsParcelable, ContactsPresenter> providesFragmentInterface(
            @NonNull ContactsFragment fragment
    ) {
        return fragment;
    }
}
