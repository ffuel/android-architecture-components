package com.a65apps.architecturecomponents.sample.data.posts;

import com.a65apps.architecturecomponents.sample.BuildConfig;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

interface PostsApi {

    @GET("search/topics?q=ruby")
    @Headers({
            "Accept: application/vnd.github.mercy-preview+json",
            "Authorization: token " + BuildConfig.GITHUB_TOKEN
    })
    Single<PostsJson> posts(@Query("page") int page, @Query("per_page") int pageSize);
}
