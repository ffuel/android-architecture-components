package com.a65apps.architecturecomponents.sample.presentation.main;

import android.Manifest;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsRequestBuffer;
import com.a65apps.architecturecomponents.domain.permissions.RequestPermissionsWorker;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.sample.DaggerTestApplicationComponent;
import com.a65apps.architecturecomponents.sample.TestApplicationComponent;
import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.sample.domain.navigation.Screen;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;
import com.a65apps.daggerarchitecturecomponents.presenter.HasPresenterSubComponentBuilders;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterSubComponentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest implements HasPresenterSubComponentBuilders {

    private MainPresenter presenter;

    @Mock
    private RequestPermissionsWorker permissionsWorker;
    @Mock
    private MainView view;

    @Inject
    PresenterSubComponentBuilderFactory componentFactory;

    @Before
    public void setup() {
        PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();
        buffer.setWorker(permissionsWorker);
        TestApplicationComponent component = DaggerTestApplicationComponent
                .builder()
                .permissionsModule(new PermissionsModule(new PermissionsManager(buffer)))
                .build();
        component.inject(this);

        presenter = PresenterInjector.build(MainPresenter.class, this, null);
    }

    @NonNull
    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }

    @Test
    public void showContactsTest() {
        when(permissionsWorker.checkPermission(eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(true);

        presenter.attachView(view);
        presenter.showContacts();

        verify(view).updateState(eq(MainState.builder().screen(Screen.CONTACTS)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build()));
    }

    @Test
    public void showPostsTest() {
        presenter.attachView(view);
        presenter.showPosts();

        verify(view).updateState(eq(MainState.builder().screen(Screen.POSTS)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build()));
    }

    @Test
    public void showMviTest() {
        presenter.attachView(view);
        presenter.showMvi();

        verify(view).updateState(eq(MainState.builder().screen(Screen.MVI)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build()));
    }

    @Test
    public void onBackPressedContactsTest() {
        when(permissionsWorker.checkPermission(eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(true);
        presenter.attachView(view);

        presenter.showContacts();
        presenter.onBackPressed();

        verify(view).updateState(eq(MainState.builder().screen(Screen.CONTACTS)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build()));
        verify(view, times(2))
                .updateState(eq(MainState.DEFAULT));
    }

    @Test
    public void onBackPressedPostsTest() {
        presenter.attachView(view);

        presenter.showPosts();
        presenter.onBackPressed();

        verify(view).updateState(eq(MainState.builder().screen(Screen.POSTS)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build()));
        verify(view, times(2))
                .updateState(eq(MainState.DEFAULT));
    }

    @Test
    public void onBackPressedMviTest() {
        presenter.attachView(view);

        presenter.showMvi();
        presenter.onBackPressed();

        verify(view).updateState(eq(MainState.builder().screen(Screen.MVI)
                .backStack(Collections.singletonList(Screen.SAMPLE))
                .build()));
        verify(view, times(2))
                .updateState(eq(MainState.DEFAULT));
    }

    @Test
    public void showSystemMessageTest() {
        presenter.attachView(view);
        presenter.getInteractor().broadcastSystemMessage("message");

        verify(view).showSystemMessage(eq("message"));
    }
}
