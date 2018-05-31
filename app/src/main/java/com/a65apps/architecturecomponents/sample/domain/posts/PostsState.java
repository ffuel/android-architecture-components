package com.a65apps.architecturecomponents.sample.domain.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.paging.PageState;
import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class PostsState extends PageState<PostState> {

    public static Builder builder() {
        return new AutoValue_PostsState.Builder();
    }

    @NonNull
    public static PostsState defaultState() {
        return PostsState.builder()
                .anchor(0)
                .count(0)
                .lastBindPosition(0)
                .items(Collections.emptyList())
                .isLoading(true)
                .isDataChanged(false)
                .isAllDataLoaded(false)
                .build();
    }

    @NonNull
    public static PostsState newPosts(@NonNull List<PostState> posts, int count, int lastBindPosition,
                                      int anchor, boolean isAllDataLoaded) {
        return PostsState.builder()
                .anchor(anchor)
                .count(count)
                .lastBindPosition(lastBindPosition)
                .items(posts)
                .isLoading(false)
                .isDataChanged(true)
                .isAllDataLoaded(isAllDataLoaded)
                .build();
    }

    @NonNull
    public PostsState loading() {
        return PostsState.builder()
                .anchor(anchor())
                .count(count())
                .lastBindPosition(lastBindPosition())
                .items(items())
                .isLoading(true)
                .isDataChanged(false)
                .isAllDataLoaded(isAllDataLoaded())
                .build();
    }

    @NonNull
    @Override
    public abstract List<PostState> items();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder count(int count);

        public abstract Builder lastBindPosition(int lastBindPosition);

        public abstract Builder anchor(int anchor);

        public abstract Builder isLoading(boolean isLoading);

        public abstract Builder isDataChanged(boolean isDataChanged);

        public abstract Builder isAllDataLoaded(boolean isAllDataLoaded);

        public abstract Builder items(List<PostState> items);

        public abstract PostsState build();
    }
}
