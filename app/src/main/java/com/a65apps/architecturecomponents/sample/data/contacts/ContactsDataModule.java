package com.a65apps.architecturecomponents.sample.data.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.domain.source.SingleSourceWithParam;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface ContactsDataModule {

    @Singleton
    @Binds
    @NonNull
    SingleSourceWithParam<ContactsListState, String> bindsContactsSource(@NonNull ContactsSource source);
}
