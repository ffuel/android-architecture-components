package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.sample.DaggerTestApplicationComponent;
import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.TestApplicationComponent;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviState;
import com.a65apps.architecturecomponents.sample.presentation.main.MainPresenter;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;
import com.a65apps.daggerarchitecturecomponents.presenter.HasPresenterSubComponentBuilders;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterComponentBuilder;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterSubComponentBuilderFactory;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MviPresenterTest implements HasPresenterSubComponentBuilders {

    @Inject
    PresenterSubComponentBuilderFactory componentFactory;

    @Mock
    private MoxyView<MviState> view;

    private MviPresenter presenter;
    private StringResources stringResources;

    @Before
    public void setup() {
        TestApplicationComponent component = DaggerTestApplicationComponent
                .builder()
                .permissionsModule(new PermissionsModule(new PermissionsManager()))
                .build();
        component.inject(this);

        stringResources = component.providesStringResources();
        when(stringResources.getString(R.string.mvi_title)).thenReturn("title");
        when(stringResources.getString(R.string.mvi_subtitle)).thenReturn("subtitle");

        MainPresenter mainPresenter = PresenterInjector.build(MainPresenter.class, this, null);
        presenter = PresenterInjector.build(MviPresenter.class,
                mainPresenter::getPresenterSubComponentBuilder, null);
    }

    @NonNull
    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }

    @Test
    public void initTest() {
        presenter.attachView(view);

        verify(view).updateState(MviState.builder()
                .title("title")
                .subtitle("subtitle")
                .build());
    }

    @Test
    public void errorTitleTest() {
        when(stringResources.getString(R.string.mvi_title)).thenThrow(new RuntimeException("error"));
        presenter.attachView(view);

        verify(view).updateState(MviState.builder()
                .title("error")
                .subtitle("subtitle")
                .build());
    }

    @Test
    public void errorSubtitleTest() {
        when(stringResources.getString(R.string.mvi_subtitle)).thenThrow(new RuntimeException("error"));
        presenter.attachView(view);

        verify(view).updateState(MviState.builder()
                .title("title")
                .subtitle("error")
                .build());
    }
}
