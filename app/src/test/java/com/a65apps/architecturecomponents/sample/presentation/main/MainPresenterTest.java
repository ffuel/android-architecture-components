package com.a65apps.architecturecomponents.sample.presentation.main;

import android.Manifest;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsRequestBuffer;
import com.a65apps.architecturecomponents.domain.permissions.RequestPermissionsWorker;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.sample.DaggerTestApplicationComponent;
import com.a65apps.architecturecomponents.sample.TestApplicationComponent;
import com.a65apps.architecturecomponents.sample.domain.main.MainState;
import com.a65apps.architecturecomponents.sample.domain.main.Screen;
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

    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }

    @Test
    public void showContactsTest() {
        when(permissionsWorker.checkPermission(eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(true);

        presenter.attachView(view);
        presenter.showContacts();

        verify(view, times(1))
                .updateState(eq(MainState.create(Screen.CONTACTS)));
    }

    @Test
    public void showPostsTest() {
        presenter.attachView(view);
        presenter.showPosts();

        verify(view, times(1))
                .updateState(eq(MainState.create(Screen.POSTS)));
    }

    @Test
    public void onBackPressedContactsTest() {
        when(permissionsWorker.checkPermission(eq(Manifest.permission.READ_CONTACTS)))
                .thenReturn(true);
        presenter.attachView(view);

        presenter.showContacts();
        presenter.onBackPressed();

        verify(view, times(1))
                .updateState(eq(MainState.create(Screen.CONTACTS)));
        verify(view, times(2))
                .updateState(eq(MainState.create(Screen.SAMPLE)));
    }

    @Test
    public void onBackPressedPostsTest() {
        presenter.attachView(view);

        presenter.showPosts();
        presenter.onBackPressed();

        verify(view, times(1))
                .updateState(eq(MainState.create(Screen.POSTS)));
        verify(view, times(2))
                .updateState(eq(MainState.create(Screen.SAMPLE)));
    }
}
