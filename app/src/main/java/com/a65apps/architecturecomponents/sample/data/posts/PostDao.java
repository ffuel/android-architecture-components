package com.a65apps.architecturecomponents.sample.data.posts;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
interface PostDao {

    @Insert(onConflict = REPLACE)
    void insert(PostDb... post);

    @Query("SELECT * FROM posts ORDER BY score DESC LIMIT :limit OFFSET :offset")
    Single<List<PostDb>> getPosts(int offset, int limit);

    @Query("SELECT COUNT(*) FROM posts")
    Single<Integer> count();
}
