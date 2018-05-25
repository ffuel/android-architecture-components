package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
abstract class PostJson {

    public static Builder builder() {
        return new AutoValue_PostJson.Builder();
    }

    public static TypeAdapter<PostJson> typeAdapter(Gson gson) {
        return new AutoValue_PostJson.GsonTypeAdapter(gson);
    }

    @Nullable
    @SerializedName("name")
    public abstract String name();

    @Nullable
    @SerializedName("display_name")
    public abstract String displayName();

    @Nullable
    @SerializedName("short_description")
    public abstract String shortDescription();

    @Nullable
    @SerializedName("description")
    public abstract String description();

    @Nullable
    @SerializedName("created_by")
    public abstract String createdBy();

    @Nullable
    @SerializedName("score")
    public abstract Double score();

    @Nullable
    @SerializedName("featured")
    public abstract Boolean featured();

    @Nullable
    @SerializedName("curated")
    public abstract Boolean curated();


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder displayName(String displayName);

        public abstract Builder shortDescription(String shortDescription);

        public abstract Builder description(String description);

        public abstract Builder createdBy(String createdBy);

        public abstract Builder score(Double score);

        public abstract Builder featured(Boolean featured);

        public abstract Builder curated(Boolean curated);

        public abstract PostJson build();
    }
}
