package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;

import javax.inject.Inject;

class DbToStateMapper extends Mapper<PostDb, PostState> {

    @Inject
    DbToStateMapper() {
//      Inject constructor
    }

    @NonNull
    @Override
    public PostState map(@NonNull PostDb postDb) {
        return PostState.builder()
                .createdBy(postDb.getCreatedBy())
                .curated(postDb.isCurated())
                .description(postDb.getDescription())
                .displayName(postDb.getDisplayName())
                .featured(postDb.isFeatured())
                .name(postDb.getName())
                .score(postDb.getScore())
                .shortDescription(postDb.getShortDescription())
                .build();
    }
}
