package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.paging.PageParcelable;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
abstract class PostsParcelable extends PageParcelable<PostParcelable> {

    public static Builder builder() {
        return new AutoValue_PostsParcelable.Builder();
    }

    @NonNull
    @Override
    public abstract List<PostParcelable> items();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder count(int count);

        public abstract Builder lastBindPosition(int lastBindPosition);

        public abstract Builder anchor(int anchor);

        public abstract Builder isLoading(boolean isLoading);

        public abstract Builder isDataChanged(boolean isDataChanged);

        public abstract Builder isAllDataLoaded(boolean isAllDataLoaded);

        public abstract Builder items(List<PostParcelable> items);

        public abstract PostsParcelable build();
    }
}
