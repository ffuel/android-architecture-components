package com.a65apps.architecturecomponents.sample.domain.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class PostsScreen implements Screen {

    public static PostsScreen create() {
        return new AutoValue_PostsScreen();
    }

    @NonNull
    @Override
    public String getScreenKey() {
        return NavigationConstants.POSTS_KEY;
    }
}
