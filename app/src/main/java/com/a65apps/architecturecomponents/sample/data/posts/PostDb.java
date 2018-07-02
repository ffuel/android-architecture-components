package com.a65apps.architecturecomponents.sample.data.posts;

import android.annotation.TargetApi;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "posts", indices = {
        @Index(value = "name", unique = true)
})
class PostDb {

    @PrimaryKey
    @ColumnInfo(name = "name")
    @NonNull
    private final String name;

    @ColumnInfo(name = "display_name")
    @NonNull
    private final String displayName;

    @ColumnInfo(name = "short_description")
    @NonNull
    private final String shortDescription;

    @ColumnInfo(name = "description")
    @NonNull
    private final String description;

    @ColumnInfo(name = "created_by")
    @NonNull
    private final String createdBy;

    @ColumnInfo(name = "score")
    private final double score;

    @ColumnInfo(name = "featured")
    private final boolean featured;

    @ColumnInfo(name = "curated")
    private final boolean curated;

    @SuppressWarnings({"checkstyle:ParameterNumber", "squid:S00107"})
    PostDb(@NonNull String name, @NonNull String displayName, @NonNull String shortDescription,
           @NonNull String description, @NonNull String createdBy, double score, boolean featured,
           boolean curated) {
        this.name = name;
        this.displayName = displayName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.createdBy = createdBy;
        this.score = score;
        this.featured = featured;
        this.curated = curated;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @NonNull
    public String getShortDescription() {
        return shortDescription;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getCreatedBy() {
        return createdBy;
    }

    public double getScore() {
        return score;
    }

    public boolean isFeatured() {
        return featured;
    }

    public boolean isCurated() {
        return curated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostDb postDb = (PostDb) o;
        return Double.compare(postDb.score, score) == 0
                && featured == postDb.featured
                && curated == postDb.curated
                && name.equals(postDb.name)
                && displayName.equals(postDb.displayName)
                && shortDescription.equals(postDb.shortDescription)
                && description.equals(postDb.description)
                && createdBy.equals(postDb.createdBy);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(name, displayName, shortDescription, description, createdBy, score, featured, curated);
    }
}
