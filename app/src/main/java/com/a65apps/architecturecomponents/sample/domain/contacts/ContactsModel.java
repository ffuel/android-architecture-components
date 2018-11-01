package com.a65apps.architecturecomponents.sample.domain.contacts;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.argument.ArgumentContainer;
import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.domain.model.BaseCompositeStateModel;
import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.domain.source.SingleSourceWithParam;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.sample.domain.main.MainInteractor;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.Single;

final class ContactsModel extends BaseCompositeStateModel<ContactsState, ContactsListState, Router>
        implements ContactsInteractor {

    @NonNull
    private final SingleSourceWithParam<ContactsListState, String> source;
    @NonNull
    private final ThreadExecutor ioExecutor;
    @NonNull
    private final ThreadExecutor uiExecutor;
    @NonNull
    private final PermissionsSource permissionsSource;
    @NonNull
    private final StringResources stringResources;
    @NonNull
    private final MainInteractor mainInteractor;

    @Inject
    ContactsModel(@NonNull Router router,
                  @NonNull SingleSourceWithParam<ContactsListState, String> source,
                  @NonNull ExecutorsFactory executors,
                  @NonNull PermissionsSource permissionsSource,
                  @NonNull StringResources stringResources,
                  @NonNull ArgumentContainer argumentContainer,
                  @NonNull MainInteractor mainInteractor) {
        super(ContactsState.create(argumentContainer.argument(String.class)),
                ContactsListState.create(Collections.emptyList()), router);
        this.source = source;
        this.ioExecutor = executors.getExecutor(SchedulerType.IO);
        this.uiExecutor = executors.getExecutor(SchedulerType.UI);
        this.permissionsSource = permissionsSource;
        this.stringResources = stringResources;
        this.mainInteractor = mainInteractor;
        loadContacts(getState());
    }

    @UiThread
    @Override
    public void query(@NonNull String query) {
        setState(ContactsState.create(query));
        loadContacts(getState());
    }

    @UiThread
    @Override
    protected void restoreDependentState(@NonNull ContactsState state) {
        loadContacts(state);
    }

    private void loadContacts(@NonNull ContactsState state) {
        addDisposable(permissionsSource.requestPermission(false, Manifest.permission.READ_CONTACTS)
                .subscribeOn(uiExecutor.getScheduler())
                .flatMap(result -> checkPermissionState(result, state))
                .doOnSuccess(this::setDependentState)
                .subscribe());
    }

    @NonNull
    private Single<ContactsListState> checkPermissionState(@NonNull PermissionState permissionState,
                                                           @NonNull ContactsState state) {
        switch (permissionState) {
            case GRANTED:
                return source.data(state.query())
                        .subscribeOn(ioExecutor.getScheduler());
            case SHOW_SETTINGS:
            case NOT_GRANTED:
                mainInteractor.broadcastSystemMessage(stringResources.getString(
                        R.string.contacts_permissions_not_granted));
                break;
            case NEED_EXPLANATION:
                return permissionsSource.requestPermission(true, Manifest.permission.READ_CONTACTS)
                        .flatMap(result -> checkPermissionState(result, state));
            default:
                break;
        }

        return Single.just(ContactsListState.create(Collections.emptyList()));
    }
}
