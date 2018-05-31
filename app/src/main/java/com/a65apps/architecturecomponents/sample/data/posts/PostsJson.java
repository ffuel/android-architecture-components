package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
abstract class PostsJson {

    public static Builder builder() {
        return new AutoValue_PostsJson.Builder();
    }

    public static TypeAdapter<PostsJson> typeAdapter(Gson gson) {
        return new AutoValue_PostsJson.GsonTypeAdapter(gson);
    }

    @Nullable
    @SerializedName("total_count")
    public abstract Integer totalCount();

    @Nullable
    @SerializedName("incomplete_results")
    public abstract Boolean incompleteResults();

    @Nullable
    @SerializedName("items")
    public abstract List<PostJson> items();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder totalCount(Integer totalCount);

        public abstract Builder incompleteResults(Boolean incompleteResults);

        public abstract Builder items(List<PostJson> items);

        public abstract PostsJson build();
    }
}
