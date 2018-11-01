package com.a65apps.architecturecomponents.sample.domain.contacts;

import android.Manifest;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.argument.ArgumentContainer;
import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.domain.source.SingleSourceWithParam;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.sample.domain.main.MainInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsModelTest {

    @Mock
    private Router router;
    @Mock
    private SingleSourceWithParam<ContactsListState, String> source;
    @Mock
    private ExecutorsFactory executors;
    @Mock
    private StringResources stringResources;
    @Mock
    private PermissionsSource permissionsSource;
    @Mock
    private ThreadExecutor executor;
    @Mock
    private ArgumentContainer argumentContainer;
    @Mock
    private MainInteractor mainInteractor;

    @Before
    public void setup() {
        when(executors.getExecutor(eq(SchedulerType.IO)))
                .thenReturn(executor);
        when(executors.getExecutor(eq(SchedulerType.UI)))
                .thenReturn(executor);
        when(executor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(permissionsSource.requestPermission(eq(false),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.GRANTED));
    }

    @Test
    public void constructorTest() {
        when(argumentContainer.argument(eq(String.class))).thenReturn("test");
        when(source.data(eq("test")))
                .thenReturn(Single.just(
                        ContactsListState.create(Collections.emptyList())));
        ContactsModel model = createModel();
        TestObserver<ContactsState> observer = new TestObserver<>();
        TestObserver<ContactsListState> observerDependent = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.observeDependentState().subscribe(observerDependent);

        observer.assertValue(ContactsState.create("test"));
        observerDependent.assertValue(ContactsListState.create(Collections.emptyList()));
    }

    @Test
    public void queryTest() {
        when(argumentContainer.argument(eq(String.class))).thenReturn("");
        when(source.data(any()))
                .thenReturn(Single.just(
                        ContactsListState.create(Collections.emptyList())));
        ContactsModel model = createModel();
        TestObserver<ContactsState> observer = new TestObserver<>();
        TestObserver<ContactsListState> observerDependent = new TestObserver<>();

        model.query("test");
        model.observeState().subscribe(observer);
        model.observeDependentState().subscribe(observerDependent);

        observer.assertValue(ContactsState.create("test"));
        observerDependent.assertValue(ContactsListState.create(Collections.emptyList()));
    }

    @Test
    public void restoreDependentStateTest() {
        when(argumentContainer.argument(eq(String.class))).thenReturn("");
        when(source.data(any()))
                .thenReturn(Single.just(
                        ContactsListState.create(Collections.emptyList())));

        ContactsModel model = createModel();
        TestObserver<ContactsListState> observerDependent = new TestObserver<>();

        model.restoreDependentState(ContactsState.create("test"));
        model.observeDependentState().subscribe(observerDependent);

        observerDependent.assertValue(ContactsListState.create(Collections.emptyList()));
    }

    @NonNull
    private ContactsModel createModel() {
        return new ContactsModel(router, source, executors, permissionsSource, stringResources, argumentContainer,
                mainInteractor);
    }
}
