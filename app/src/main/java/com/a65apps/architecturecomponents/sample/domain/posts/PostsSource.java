package com.a65apps.architecturecomponents.sample.domain.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.source.PutSource;
import com.a65apps.architecturecomponents.domain.source.SingleSourceWithParam;

import java.util.List;

import io.reactivex.Single;

public interface PostsSource extends SingleSourceWithParam<List<PostState>, PostsRequest>,
        PutSource<List<PostState>> {

    @NonNull
    Single<Integer> count();
}
