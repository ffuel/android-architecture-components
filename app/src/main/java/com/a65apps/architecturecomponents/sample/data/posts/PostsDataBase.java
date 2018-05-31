package com.a65apps.architecturecomponents.sample.data.posts;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

@Database(entities = {PostDb.class}, version = 1)
abstract class PostsDataBase extends RoomDatabase {

    @NonNull
    public abstract PostDao getPostDao();
}
