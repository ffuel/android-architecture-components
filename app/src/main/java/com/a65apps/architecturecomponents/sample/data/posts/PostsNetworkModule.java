package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PostsNetworkModule {

    @Singleton
    @NonNull
    @Provides
    Gson providesGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(PostsTypeAdapterFactory.create())
                .create();
    }

    @Singleton
    @NonNull
    @Provides
    PostsApi providesPostsApi(@NonNull Gson gson) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level level;
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY;
        } else {
            level = HttpLoggingInterceptor.Level.BASIC;
        }
        logging.setLevel(level);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(constructBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(PostsApi.class);
    }

    @NonNull
    protected HttpUrl constructBaseUrl() {
        return new HttpUrl.Builder()
                .host("api.github.com")
                .scheme("https")
                .build();
    }
}
