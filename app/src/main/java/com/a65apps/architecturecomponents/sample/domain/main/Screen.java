package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.a65apps.architecturecomponents.sample.R;

public enum Screen {
    SAMPLE("screen_sample", R.string.sample_title),
    CONTACTS("screen_contacts", R.string.contacts_title),
    PERMISSION_EXPLANATION("screen_permission_explanation", R.string.permissions_title),
    POSTS("posts", R.string.posts_title);

    @NonNull
    private String name;
    @StringRes
    private int title;

    @NonNull
    public static Screen fromString(@NonNull String key) {
        if (SAMPLE.name.equals(key)) {
            return SAMPLE;
        } else if (CONTACTS.name.equals(key)) {
            return CONTACTS;
        } else if (PERMISSION_EXPLANATION.name.equals(key)) {
            return PERMISSION_EXPLANATION;
        } else if (POSTS.name.equals(key)) {
            return POSTS;
        }

        throw new IllegalArgumentException("Unknown screen: " + key);
    }

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
