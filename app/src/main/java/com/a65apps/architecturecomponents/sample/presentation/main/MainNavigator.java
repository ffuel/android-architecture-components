package com.a65apps.architecturecomponents.sample.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.a65apps.architecturecomponents.sample.domain.main.Screen;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsFragment;
import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationFragment;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsFragment;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleFragment;
import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;

import ru.terrakok.cicerone.android.SupportAppNavigator;

public final class MainNavigator extends SupportAppNavigator {

    @NonNull
    private final FragmentActivity activity;

    MainNavigator(@NonNull FragmentActivity activity, @NonNull ContainerIdProvider idProvider) {
        super(activity, idProvider.get());
        this.activity = activity;
    }

    @Override
    protected Intent createActivityIntent(Context context, String screenKey, Object data) {
        return null;
    }

    @Override
    protected Fragment createFragment(@NonNull String screenKey, @Nullable Object data) {
        Screen screen = Screen.fromString(screenKey);
        switch (screen) {
            case SAMPLE:
                return SampleFragment.newInstance();
            case CONTACTS:
                return ContactsFragment.newInstance();
            case PERMISSION_EXPLANATION:
                if (!(data instanceof String[])) {
                    throw new IllegalArgumentException("Wrong data for screen " + screen.getName());
                }
                return PermissionsExplanationFragment.newInstance((String[]) data);
            case POSTS:
                return PostsFragment.newInstance();
            default:
                break;
        }

        throw new IllegalArgumentException("Unknown screen key: " + screenKey);
    }

    @Override
    protected void showSystemMessage(String message) {
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
