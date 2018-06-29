package com.a65apps.architecturecomponents.sample.domain.main;

import android.Manifest;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.sample.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainModelTest {

    @Mock
    private Router router;
    @Mock
    private StringResources stringResources;
    @Mock
    private PermissionsSource permissionsSource;

    @Before
    public void setup() {
        when(stringResources.getString(eq(R.string.contacts_permissions_not_granted)))
                .thenReturn("not granted");
    }

    @Test
    public void constructorTest() {
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.observeState().subscribe(observer);

        observer.assertValue(MainState.create(Screen.SAMPLE));
    }

    @Test
    public void firstStartTest() {
        MainModel model = createModel();

        model.firstStart(true);
        model.firstStart(false);

        verify(router, times(1))
                .newRootScreen(eq(Screen.SAMPLE.getName()));
    }

    @Test
    public void navigateContactsGrantedTest() {
        when(permissionsSource.requestPermission(eq(false),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.GRANTED));
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigateContacts();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.create(Screen.CONTACTS));
        verify(permissionsSource, times(1))
                .requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(router, times(1))
                .navigateTo(eq(Screen.CONTACTS.getName()));
    }

    @Test
    public void navigateContactsNotGrantedTest() {
        when(permissionsSource.requestPermission(eq(false),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.NOT_GRANTED));
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigateContacts();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.create(Screen.SAMPLE));
        verify(permissionsSource, times(1))
                .requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(router, times(1))
                .showSystemMessage(eq("not granted"));
    }

    @Test
    public void navigateContactsShowSettingsTest() {
        when(permissionsSource.requestPermission(eq(false),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.SHOW_SETTINGS));
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigateContacts();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.create(Screen.SAMPLE));
        verify(permissionsSource, times(1))
                .requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(router, times(1))
                .showSystemMessage(eq("not granted"));
    }

    @Test
    public void navigateContactsNeedExplanationTest() {
        when(permissionsSource.requestPermission(eq(false),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.NEED_EXPLANATION));
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigateContacts();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.create(Screen.PERMISSION_EXPLANATION));
        verify(permissionsSource, times(1))
                .requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(router, times(1))
                .navigateTo(eq(Screen.PERMISSION_EXPLANATION.getName()),
                        eq(new String[] {"not granted"}));
    }

    @Test
    public void forceContactsPermissionsTest() {
        when(permissionsSource.requestPermission(eq(true),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.GRANTED));
        MainModel model = createModel();

        model.forceContactsPermissions();

        verify(permissionsSource, times(1))
                .requestPermission(eq(true), eq(Manifest.permission.READ_CONTACTS));
    }

    @Test
    public void onBackTest() {
        when(permissionsSource.requestPermission(eq(false),
                eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(Single.just(PermissionState.GRANTED))
                .thenReturn(Single.just(PermissionState.NEED_EXPLANATION));

        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.onBack();
        model.observeState().subscribe(observer);
        observer.assertValue(MainState.create(Screen.SAMPLE));

        observer = new TestObserver<>();
        model.navigateContacts();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.create(Screen.CONTACTS),
                MainState.create(Screen.SAMPLE));

        observer = new TestObserver<>();
        model.navigateContacts();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.create(Screen.PERMISSION_EXPLANATION),
                MainState.create(Screen.CONTACTS));

        observer = new TestObserver<>();
        model.navigatePosts();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.create(Screen.POSTS),
                MainState.create(Screen.SAMPLE));

        verify(router, times(4)).exit();
    }

    @Test
    public void navigatePostsTest() {
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigatePosts();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.create(Screen.POSTS));
        verify(router, times(1))
                .navigateTo(eq(Screen.POSTS.getName()));
    }

    @NonNull
    private MainModel createModel() {
        return new MainModel(router, stringResources, permissionsSource);
    }
}
