package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.content.SharedPreferences;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionState;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.sample.DaggerTestApplicationComponent;
import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.TestApplicationComponent;
import com.a65apps.architecturecomponents.sample.data.sample.PreferenceKeys;
import com.a65apps.architecturecomponents.sample.domain.sample.SampleState;
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

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SamplePresenterTest implements HasPresenterSubComponentBuilders {

    private static final ConnectionState CONNECTED = ConnectionState.builder()
            .type(1)
            .detailedState(ConnectionState.DetailedState.CONNECTED)
            .extraInfo("")
            .isAvailable(true)
            .isFailover(false)
            .isRoaming(false)
            .reason("")
            .state(ConnectionState.State.CONNECTED)
            .subtype(1)
            .subtypeName("")
            .typeName("")
            .build();

    private SamplePresenter presenter;

    @Inject
    PresenterSubComponentBuilderFactory componentFactory;

    @Mock
    private MoxyView<SampleState> view;
    @Mock
    private SharedPreferences.Editor editor;

    private SharedPreferences sharedPreferences;
    private StringResources stringResources;
    private ConnectionReceiverSource connectionSource;
    private SingleSource<String> source;

    @Before
    public void setup() {
        TestApplicationComponent component = DaggerTestApplicationComponent
                .builder()
                .permissionsModule(new PermissionsModule(new PermissionsManager()))
                .build();
        component.inject(this);
        sharedPreferences = component.providesSharedPreferences();
        stringResources = component.providesStringResources();
        connectionSource = component.providesConnectionReceiverSource();
        source = component.providesSampleFakeNetworkSource();

        when(stringResources.getString(eq(R.string.hello_text)))
                .thenReturn("Hello World!");
        when(stringResources.getString(eq(R.string.no_connection_text)))
                .thenReturn("no connection");
        when(source.data())
                .thenReturn(Single.just("ArchitectureComponents"));

        MainPresenter mainPresenter = PresenterInjector.build(MainPresenter.class, this);
        presenter = PresenterInjector.build(SamplePresenter.class,
                mainPresenter::getPresenterSubComponentBuilder);

        when(sharedPreferences.getString(eq(PreferenceKeys.SAMPLE_DATA_KEY), eq("")))
                .thenReturn("ArchitectureComponents");
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(eq(PreferenceKeys.SAMPLE_DATA_KEY), eq("ArchitectureComponents")))
                .thenReturn(editor);
        when(editor.commit()).thenReturn(true);
    }

    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }

    @Test
    public void refresh() {
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED));
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(CONNECTED));
        presenter.attachView(view);
        presenter.refresh();

        verify(view, times(2))
                .updateState(eq(SampleState
                        .create(SampleState.State.COMPLETE, "Hello World!",
                                "ArchitectureComponents", "")));
    }
}
