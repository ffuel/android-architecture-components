package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.domain.navigation.ContactsScreen;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsFragment;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;

import javax.inject.Inject;

class ContactsScreenFactory implements FragmentFactory {

    @Inject
    ContactsScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @NonNull Screen screen) {
        if (!(screen instanceof ContactsScreen)) {
            throw new IllegalArgumentException("wrong screen type: " + screen.getClass().getName());
        }

        ContactsScreen contactsScreen = (ContactsScreen) screen;
        bundle.putString(ContactsFragment.SEARCH_ARG, contactsScreen.query());
        return ContactsFragment.newInstance(bundle);
    }
}
