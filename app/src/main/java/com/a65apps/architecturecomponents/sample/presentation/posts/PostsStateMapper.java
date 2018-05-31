package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsState;

import javax.inject.Inject;

import io.reactivex.Observable;

class PostsStateMapper extends StateToParcelableMapper<PostsState, PostsParcelable> {

    @Inject
    PostsStateMapper() {
        super();
    }

    @NonNull
    @Override
    public PostsParcelable map(@NonNull PostsState postsState) {
        return PostsParcelable.builder()
                .anchor(postsState.anchor())
                .count(postsState.count())
                .lastBindPosition(postsState.lastBindPosition())
                .isLoading(postsState.isLoading())
                .isDataChanged(postsState.isDataChanged())
                .isAllDataLoaded(postsState.isAllDataLoaded())
                .items(Observable.fromIterable(postsState.items())
                        .map(post -> PostParcelable.builder()
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
