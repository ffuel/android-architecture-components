package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;

import javax.inject.Inject;

class StateToDbMapper extends Mapper<PostState, PostDb> {

    @Inject
    StateToDbMapper() {
//      Inject constructor
    }

    @NonNull
    @Override
    public PostDb map(@NonNull PostState postState) {
        return new PostDb(postState.name(), postState.displayName(), postState.shortDescription(),
                postState.description(), postState.createdBy(), postState.score(),
                postState.featured(), postState.curated());
    }
}
