package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
@SuppressWarnings("squid:S1118")
public abstract class PostsTypeAdapterFactory implements TypeAdapterFactory {

    @NonNull
    public static TypeAdapterFactory create() {
        return new AutoValueGson_PostsTypeAdapterFactory();
    }
}
