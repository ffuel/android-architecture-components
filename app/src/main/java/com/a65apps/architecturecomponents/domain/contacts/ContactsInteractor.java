package com.a65apps.architecturecomponents.domain.contacts;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

public interface ContactsInteractor extends CompositeStateInteractor<ContactsState, ContactsListState,
        Router> {

    void query(@NonNull  String query);
}
