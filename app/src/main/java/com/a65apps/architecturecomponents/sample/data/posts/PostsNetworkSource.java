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

class PostsNetworkSource implements PostsSource {

    @NonNull
    private final PostsApi api;
    @NonNull
    private final Mapper<PostsJson, List<PostState>> mapper;

    @Inject
    PostsNetworkSource(@NonNull PostsApi api,
                       @NonNull Mapper<PostsJson, List<PostState>> mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public Single<Integer> count() {
        return Single.error(new UnsupportedOperationException());
    }

    @NonNull
    @Override
    public Single<List<PostState>> data(@NonNull PostsRequest postsRequest) {
        int page = (int) ((float) postsRequest.offset() / (float) postsRequest.limit()) + 1;
        return api.posts(page, postsRequest.limit())
                .map(mapper::map);
    }

    @NonNull
    @Override
    public Completable putData(@NonNull List<PostState> data) {
        return Completable.error(new UnsupportedOperationException());
    }
}
