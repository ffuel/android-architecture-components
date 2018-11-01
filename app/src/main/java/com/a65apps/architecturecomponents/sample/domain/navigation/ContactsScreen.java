package com.a65apps.architecturecomponents.sample.domain.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ContactsScreen implements Screen {

    public static ContactsScreen create(String query) {
        return new AutoValue_ContactsScreen(query);
    }

    @NonNull
    public abstract String query();

    @NonNull
    @Override
    public String getScreenKey() {
        return NavigationConstants.CONTACTS_KEY;
    }
}
