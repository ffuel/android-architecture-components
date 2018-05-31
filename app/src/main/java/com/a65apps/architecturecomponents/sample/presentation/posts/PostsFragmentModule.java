package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.di.ViewModule;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsState;
import com.a65apps.daggerarchitecturecomponents.view.DaggerViewModule;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsFragmentModule extends DaggerViewModule<PostsState, PostsParcelable,
        PostsStateMapper, PostsParcelMapper> {

    @Provides
    @NonNull
    ViewModule<PostsState, PostsParcelable, PostsStateMapper,
            PostsParcelMapper> providesModule() {
        return new ViewModule<PostsState, PostsParcelable, PostsStateMapper,
                PostsParcelMapper>() {
            @NonNull
            @Override
            public PostsStateMapper provideStateToParcelableMapper() {
                return new PostsStateMapper();
            }

            @NonNull
            @Override
            public PostsParcelMapper provideParcelableToStateMapper() {
                return new PostsParcelMapper();
            }
        };
    }
}
