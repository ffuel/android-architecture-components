package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import dagger.Module;
import okhttp3.HttpUrl;

@Module
public class TestPostsNetworkModule extends PostsNetworkModule {

    @NonNull
    @Override
    protected HttpUrl constructBaseUrl() {
        return new HttpUrl.Builder()
                .host("127.0.0.1")
                .scheme("http")
                .port(8888)
                .build();
    }
}
