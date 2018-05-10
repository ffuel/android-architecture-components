package com.a65apps.architecturecomponents.presentation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.contacts.ContactsFragment;
import com.a65apps.architecturecomponents.presentation.contacts.ContactsFragmentModule;
import com.a65apps.architecturecomponents.presentation.sample.SampleFragment;
import com.a65apps.architecturecomponents.presentation.sample.SampleFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentBuilder {

    @ContributesAndroidInjector(modules = SampleFragmentModule.class)
    @NonNull
    SampleFragment bindSampleFragment();

    @ContributesAndroidInjector(modules = ContactsFragmentModule.class)
    @NonNull
    ContactsFragment bindContactsFragment();
}