package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsPresenter;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsPresenterModule;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsPresenter;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsPresenterModule;
import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenter;
import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenterModule;
import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;

import dagger.Module;

@Module
interface ChildPresenterComponent {

    @ContributesPresenterInjector(modules = SamplePresenterModule.class, isChild = true)
    SamplePresenter bindSamplePresenter();

    @ContributesPresenterInjector(modules = ContactsPresenterModule.class, isChild = true)
    ContactsPresenter bindContactsPresenter();

    @ContributesPresenterInjector(modules = PostsPresenterModule.class, isChild = true)
    PostsPresenter bindPostsPresenter();
}
