package com.a65apps.architecturecomponents.presentation.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.contacts.ContactsInteractor;
import com.a65apps.architecturecomponents.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.domain.contacts.ContactsState;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.moxyarchitecturecomponents.presenter.MoxyCompositeStatePresenter;
import com.a65aps.moxyarchitecturecomponents.view.MoxyCompositeStateView;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class ContactsPresenter extends MoxyCompositeStatePresenter<ContactsState, ContactsListState,
        MoxyCompositeStateView<ContactsState, ContactsListState>, ContactsInteractor, Router> {

    @Inject
    public ContactsPresenter(@NonNull ExecutorsFactory executors, @NonNull ContactsInteractor interactor,
                             @NonNull ApplicationLogger logger) {
        super(executors, interactor, logger);
    }

    void query(@NonNull String query) {
        getInteractor().query(query);
    }
}
