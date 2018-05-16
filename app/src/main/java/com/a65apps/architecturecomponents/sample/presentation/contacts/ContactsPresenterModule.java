package com.a65apps.architecturecomponents.sample.presentation.contacts;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsDomainModule;

import dagger.Module;

@Module(includes = ContactsDomainModule.class)
public class ContactsPresenterModule {
}
