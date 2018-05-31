package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsRequest;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

class PostsDataBaseSource implements PostsSource {

    @NonNull
    private final PostDao postDao;
    @NonNull
    private final Mapper<PostDb, PostState> dbToStateMapper;
    @NonNull
    private final Mapper<PostState, PostDb> stateToDbMapper;

    @Inject
    PostsDataBaseSource(@NonNull PostsDataBase dataBase,
                        @NonNull Mapper<PostDb, PostState> dbToStateMapper,
                        @NonNull Mapper<PostState, PostDb> stateToDbMapper) {
        postDao = dataBase.getPostDao();
        this.dbToStateMapper = dbToStateMapper;
        this.stateToDbMapper = stateToDbMapper;
    }

    @NonNull
    @Override
    public Single<Integer> count() {
        return postDao.count();
    }

    @NonNull
    @Override
    public Single<List<PostState>> data(@NonNull PostsRequest postsRequest) {
        return postDao.getPosts(postsRequest.offset(), postsRequest.limit())
                .map(dbToStateMapper::map);
    }

    @NonNull
    @Override
    public Completable putData(@NonNull List<PostState> data) {
        return Completable.fromAction(() -> {
            postDao.insert(stateToDbMapper.map(data).toArray(new PostDb[data.size()]));
        });
    }
}
