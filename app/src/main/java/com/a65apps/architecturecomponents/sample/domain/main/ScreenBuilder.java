package com.a65apps.architecturecomponents.sample.domain.main;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.domain.navigation.ContactsScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.MviScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PermissionExplanationScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PostsScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.SampleScreen;

import java.util.Collections;

class ScreenBuilder {

    @NonNull
    private final StringResources stringResources;

    ScreenBuilder(@NonNull StringResources stringResources) {
        this.stringResources = stringResources;
    }

    @NonNull
    Screen build(@NonNull com.a65apps.architecturecomponents.sample.domain.navigation.Screen screen) {
        switch (screen) {
            case SAMPLE:
                return SampleScreen.create();
            case CONTACTS:
                return ContactsScreen.create("тест");
            case PERMISSION_EXPLANATION:
                return PermissionExplanationScreen.create(Collections
                        .singletonList(stringResources.getString(R.string.contacts_permissions_not_granted)));
            case POSTS:
                return PostsScreen.create();
            case MVI:
                return MviScreen.create();
            default:
                break;
        }

        throw new IllegalArgumentException("Unknown screen: " + screen.getName());
    }
}
