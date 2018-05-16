package com.a65apps.architecturecomponents.sample.presentation.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;

import javax.inject.Inject;

public class ContactsParcelMapper extends ParcelableToStateMapper<ContactsParcelable, ContactsState> {

    @Inject
    public ContactsParcelMapper() {
        // @Inject constructor
    }

    @NonNull
    @Override
    public ContactsState map(@NonNull ContactsParcelable contactsParcelable) {
        return ContactsState.create(contactsParcelable.query());
    }
}
