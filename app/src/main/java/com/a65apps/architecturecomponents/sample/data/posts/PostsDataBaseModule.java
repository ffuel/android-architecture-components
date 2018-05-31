package com.a65apps.architecturecomponents.sample.data.posts;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsDataBaseModule {

    @Singleton
    @NonNull
    @Provides
    PostsDataBase providesPostsDataBase(@NonNull Context context) {
        return Room.databaseBuilder(context, PostsDataBase.class, "posts")
                .build();
    }
}
