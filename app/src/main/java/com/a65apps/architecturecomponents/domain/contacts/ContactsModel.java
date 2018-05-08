package com.a65apps.architecturecomponents.domain.contacts;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.model.BaseCompositeStateModel;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.domain.source.SingleSourceWithParam;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import java.util.Collections;

import javax.inject.Inject;

final class ContactsModel extends BaseCompositeStateModel<ContactsState, ContactsListState, Router>
        implements ContactsInteractor {

    @NonNull
    private final SingleSourceWithParam<ContactsListState, String> source;
    @NonNull
    private final ThreadExecutor executor;

    @Inject
    ContactsModel(@NonNull Router router,
                  @NonNull SingleSourceWithParam<ContactsListState, String> source,
                  @NonNull ExecutorsFactory executors) {
        super(ContactsState.create(""), ContactsListState.create(Collections.emptyList()), router);
        this.source = source;
        this.executor = executors.getExecutor(SchedulerType.IO);
        loadContacts(getState());
    }

    @Override
    public void query(@NonNull String query) {
        setState(ContactsState.create(query));
        loadContacts(getState());
    }

    @Override
    protected void restoreDependentState(@NonNull ContactsState state) {
        loadContacts(state);
    }

    private void loadContacts(@NonNull ContactsState state) {
        addDisposable(source.data(state.query())
                .subscribeOn(executor.getScheduler())
                .doOnSuccess(this::setDependentState)
                .subscribe());
    }
}
