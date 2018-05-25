package com.a65apps.architecturecomponents.sample.presentation.posts;

import com.a65apps.architecturecomponents.sample.domain.posts.PostsDomainModule;

import dagger.Module;

@Module(includes = PostsDomainModule.class)
public interface PostsPresenterModule {
}
