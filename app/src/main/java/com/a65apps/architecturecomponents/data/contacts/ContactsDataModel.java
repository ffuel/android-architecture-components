package com.a65apps.architecturecomponents.data.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.contacts.ContactsListState;
import com.a65aps.architecturecomponents.domain.source.SingleSourceWithParam;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface ContactsDataModel {

    @Singleton
    @Binds
    @NonNull
    SingleSourceWithParam<ContactsListState, String> bindsContactsSource(@NonNull ContactsSource source);
}
