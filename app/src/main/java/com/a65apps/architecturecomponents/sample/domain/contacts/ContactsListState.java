package com.a65apps.architecturecomponents.sample.domain.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.google.auto.value.AutoValue;

import net.jcip.annotations.ThreadSafe;

import java.util.List;

@AutoValue
@ThreadSafe
public abstract class ContactsListState implements State {

    public static ContactsListState create(List<ContactState> contacts) {
        return new AutoValue_ContactsListState(contacts);
    }

    @NonNull
    public abstract List<ContactState> contacts();
}
