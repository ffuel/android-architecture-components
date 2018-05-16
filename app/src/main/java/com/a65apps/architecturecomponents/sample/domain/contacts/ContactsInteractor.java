package com.a65apps.architecturecomponents.sample.domain.contacts;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

public interface ContactsInteractor extends CompositeStateInteractor<ContactsState, ContactsListState,
        Router> {

    @UiThread
    void query(@NonNull  String query);
}
