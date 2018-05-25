package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.data.common.SourceType;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsRequest;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsSource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

class PostsDataSource implements PostsSource {

    @NonNull
    private final PostsSource network;
    @NonNull
    private final PostsSource db;

    @Inject
    PostsDataSource(@NonNull Map<SourceType, PostsSource> sources) {
        network = sources.get(SourceType.NETWORK);
        db = sources.get(SourceType.DB);
    }

    @NonNull
    @Override
    public Single<Integer> count() {
        return db.count();
    }

    @NonNull
    @Override
    public Single<List<PostState>> data(@NonNull PostsRequest postsRequest) {
        if (postsRequest.forceNetwork()) {
            return network.data(postsRequest)
                    .flatMapCompletable(db::putData)
                    .andThen(db.data(postsRequest));
        }

        return db.data(postsRequest);
    }

    @NonNull
    @Override
    public Completable putData(@NonNull List<PostState> data) {
        return db.putData(data);
    }
}
