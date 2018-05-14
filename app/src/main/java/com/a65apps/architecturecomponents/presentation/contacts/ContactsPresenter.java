package com.a65apps.architecturecomponents.presentation.contacts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.contacts.ContactsInteractor;
import com.a65apps.architecturecomponents.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.domain.main.MainInteractor;
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

    @NonNull
    private final MainInteractor mainInteractor;

    @Inject
    public ContactsPresenter(@NonNull ExecutorsFactory executors, @NonNull ContactsInteractor interactor,
                             @NonNull ApplicationLogger logger, @NonNull MainInteractor mainInteractor) {
        super(executors, interactor, logger);
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void onBackPressed() {
        mainInteractor.onBack();
    }

    void query(@NonNull String query) {
        getInteractor().query(query);
    }
}
