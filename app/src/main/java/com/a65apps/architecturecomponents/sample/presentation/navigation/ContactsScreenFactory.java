package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsFragment;
import com.a65apps.ciceronearchitecturecomponents.FragmentFactory;

import javax.inject.Inject;

class ContactsScreenFactory implements FragmentFactory {

    @Inject
    ContactsScreenFactory() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Fragment build(@NonNull Bundle bundle, @Nullable Object data) {
        return ContactsFragment.newInstance();
    }
}
