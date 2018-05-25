package com.a65apps.architecturecomponents.sample.domain.posts;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PostsRequest {

    public static PostsRequest create(int offset, int limit, boolean forceNetwork) {
        return new AutoValue_PostsRequest(offset, limit, forceNetwork);
    }

    public abstract int offset();

    public abstract int limit();

    public abstract boolean forceNetwork();
}
