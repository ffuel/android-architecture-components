package com.a65apps.architecturecomponents.sample.presentation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsFragment;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsFragmentModule;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsFragment;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsFragmentModule;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleFragment;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleFragmentModule;

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

    @ContributesAndroidInjector(modules = PostsFragmentModule.class)
    @NonNull
    PostsFragment bindPostsFragment();
}
