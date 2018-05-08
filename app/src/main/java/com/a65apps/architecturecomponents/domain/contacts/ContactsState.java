package com.a65apps.architecturecomponents.domain.contacts;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

import net.jcip.annotations.ThreadSafe;

@AutoValue
@ThreadSafe
public abstract class ContactsState implements State {

    public static ContactsState create(String query) {
        return new AutoValue_ContactsState(query);
    }

    @NonNull
    public abstract String query();
}
