package com.a65apps.architecturecomponents.sample.presentation.contacts;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsInteractor;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.sample.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.moxyarchitecturecomponents.presenter.MoxyCompositeStatePresenter;
import com.a65apps.moxyarchitecturecomponents.view.MoxyCompositeStateView;
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

    @UiThread
    @Override
    public void onBackPressed() {
        mainInteractor.onBack();
    }

    @UiThread
    void query(@NonNull String query) {
        getInteractor().query(query);
    }
}
