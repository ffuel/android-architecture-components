package com.a65apps.architecturecomponents.presentation.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.contacts.ContactsState;
import com.a65aps.architecturecomponents.di.ViewModule;
import com.a65aps.daggerarchitecturecomponents.view.DaggerViewModule;

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
}
