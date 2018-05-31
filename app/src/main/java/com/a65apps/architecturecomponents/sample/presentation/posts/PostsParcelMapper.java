package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsState;

import javax.inject.Inject;

import io.reactivex.Observable;

class PostsParcelMapper extends ParcelableToStateMapper<PostsParcelable, PostsState> {

    @Inject
    PostsParcelMapper() {
        // @Inject constructor
    }

    @NonNull
    @Override
    public PostsState map(@NonNull PostsParcelable postsParcelable) {
        return PostsState.builder()
                .anchor(postsParcelable.anchor())
                .count(postsParcelable.count())
                .lastBindPosition(postsParcelable.lastBindPosition())
                .isLoading(postsParcelable.isLoading())
                .isDataChanged(postsParcelable.isDataChanged())
                .isAllDataLoaded(postsParcelable.isAllDataLoaded())
                .error(postsParcelable.error())
                .items(Observable.fromIterable(postsParcelable.items())
                        .map(post -> PostState.builder()
                                .createdBy(post.createdBy())
                                .curated(post.curated())
                                .description(post.description())
                                .displayName(post.displayName())
                                .featured(post.featured())
                                .name(post.name())
                                .score(post.score())
                                .shortDescription(post.shortDescription())
                                .build())
                        .toList()
                        .blockingGet())
                .build();
    }
}
