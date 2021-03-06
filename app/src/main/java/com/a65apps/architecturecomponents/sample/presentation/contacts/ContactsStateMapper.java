package com.a65apps.architecturecomponents.sample.presentation.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;

import javax.inject.Inject;

public class ContactsStateMapper extends StateToParcelableMapper<ContactsState, ContactsParcelable> {

    @Inject
    public ContactsStateMapper() {
        super();
    }

    @NonNull
    @Override
    public ContactsParcelable map(@NonNull ContactsState contactsState) {
        return ContactsParcelable.create(contactsState.query());
    }
}
