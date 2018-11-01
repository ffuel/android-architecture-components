package com.a65apps.architecturecomponents.sample.domain.main;

import android.Manifest;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.domain.navigation.ContactsScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.MviScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PermissionExplanationScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PostsScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.SampleScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.Screen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
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

        observer.assertValue(MainState.DEFAULT);
    }

    @Test
    public void firstStartTest() {
        MainModel model = createModel();

        model.firstStart(true);
        model.firstStart(false);

        verify(router, times(1))
                .newRootScreen(eq(SampleScreen.create()));
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

        observer.assertValue(MainState.builder().screen(Screen.CONTACTS)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build());
        verify(permissionsSource, times(1))
                .requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(router, times(1))
                .navigateTo(eq(ContactsScreen.create("тест")));
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

        observer.assertValue(MainState.DEFAULT);
        verify(permissionsSource).requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(model).broadcastSystemMessage(eq("not granted"));
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

        observer.assertValue(MainState.DEFAULT);
        verify(permissionsSource).requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(model).broadcastSystemMessage(eq("not granted"));
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

        observer.assertValue(MainState.builder().screen(Screen.PERMISSION_EXPLANATION)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build());
        verify(permissionsSource).requestPermission(eq(false), eq(Manifest.permission.READ_CONTACTS));
        verify(router).navigateTo(eq(PermissionExplanationScreen.create(Collections.singletonList("not granted"))));
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
        observer.assertValue(MainState.DEFAULT);

        observer = new TestObserver<>();
        model.navigateContacts();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.builder().screen(Screen.CONTACTS)
                        .backStack(Collections.singletonList(Screen.SAMPLE))
                        .build(),
                MainState.DEFAULT);

        observer = new TestObserver<>();
        model.navigateContacts();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.builder().screen(Screen.PERMISSION_EXPLANATION)
                        .backStack(Collections.singletonList(Screen.SAMPLE))
                        .build(),
                MainState.DEFAULT);

        observer = new TestObserver<>();
        model.navigatePosts();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.builder().screen(Screen.POSTS)
                        .backStack(Collections.singletonList(Screen.SAMPLE))
                        .build(),
                MainState.DEFAULT);

        observer = new TestObserver<>();
        model.navigateMvi();
        model.observeState().subscribe(observer);
        model.onBack();
        observer.assertValues(MainState.builder().screen(Screen.MVI)
                        .backStack(Collections.singletonList(Screen.SAMPLE))
                        .build(),
                MainState.DEFAULT);

        verify(router, times(5)).exit();
    }

    @Test
    public void navigatePostsTest() {
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigatePosts();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.builder().screen(Screen.POSTS)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build());
        verify(router).navigateTo(eq(PostsScreen.create()));
    }

    @Test
    public void navigateMviTest() {
        MainModel model = createModel();
        TestObserver<MainState> observer = new TestObserver<>();

        model.navigateMvi();
        model.observeState().subscribe(observer);

        observer.assertValue(MainState.builder().screen(Screen.MVI)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build());
        verify(router).navigateTo(eq(MviScreen.create()));
    }

    @NonNull
    private MainModel createModel() {
        return spy(new MainModel(router, stringResources, permissionsSource));
    }
}
