package com.a65apps.architecturecomponents.presentation.contacts;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ContactsParcelable implements Parcelable {

    public static ContactsParcelable create(String query) {
        return new AutoValue_ContactsParcelable(query);
    }

    @NonNull
    public abstract String query();
}
