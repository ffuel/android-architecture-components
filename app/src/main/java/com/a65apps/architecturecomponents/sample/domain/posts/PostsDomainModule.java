package com.a65apps.architecturecomponents.sample.domain.posts;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;

@Module
public interface PostsDomainModule {

    @Binds
    @NonNull
    PostsInteractor bindsTo(@NonNull PostsModel model);
}
