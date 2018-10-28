package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.a65apps.architecturecomponents.sample.R;

public enum Screen {
    SAMPLE(MainConstants.SAMPLE_KEY, R.string.sample_title),
    CONTACTS(MainConstants.CONTACTS_KEY, R.string.contacts_title),
    PERMISSION_EXPLANATION(MainConstants.PERMISSION_EXPLANATION_KEY, R.string.permissions_title),
    POSTS(MainConstants.POSTS_KEY, R.string.posts_title),
    MVI(MainConstants.MVI_KEY, R.string.mvi_screen_title);

    @NonNull
    private String name;
    @StringRes
    private int title;

    Screen(@NonNull String name, @StringRes int title) {
        this.name = name;
        this.title = title;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @StringRes
    public int getTitle() {
        return title;
    }
}
