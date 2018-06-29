package com.a65apps.architecturecomponents.sample.domain.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleModelTest {

    @Mock
    private SampleSource sampleSource;
    @Mock
    private ConnectionReceiverSource connectionSource;
    @Mock
    private ExecutorsFactory executorsFactory;
    @Mock
    private Router router;

    @Before
    public void setup() {
        when(sampleSource.text()).thenReturn("test");
        when(sampleSource.noConnectionText()).thenReturn("no connection");
    }

    @Test
    public void constructorTest() {
        SampleModel model = createModel();
        TestObserver<SampleState> observer = new TestObserver<>();

        model.observeState().subscribe(observer);

        observer.assertValue(SampleState.create(SampleState.State.LOADING, "test",
                "", ""));
    }

    @Test
    public void getDataTest() {
        when(sampleSource.data()).thenReturn(Single.just("data"));

        SampleModel model = createModel();
        TestObserver<SampleState> observer = new TestObserver<>();

        model.getData().subscribe(observer);

        observer.assertValue(SampleState.create(SampleState.State.COMPLETE, "test",
                "data", ""));
    }

    @Test
    public void tryGetDataCachedSuccessTest() {
        when(sampleSource.cachedData()).thenReturn(Single.just("data"));

        SampleModel model = createModel();
        TestObserver<SampleState> observer = new TestObserver<>();

        model.tryGetDataCached().subscribe(observer);

        observer.assertValue(SampleState.create(SampleState.State.COMPLETE, "test",
                "data", ""));
    }

    @Test
    public void tryGetDataCachedErrorTest() {
        when(sampleSource.cachedData()).thenReturn(Single.just(""));

        SampleModel model = createModel();
        TestObserver<SampleState> observer = new TestObserver<>();

        model.tryGetDataCached().subscribe(observer);

        observer.assertValue(SampleState.create(SampleState.State.ERROR, "test",
                "", ""));
    }

    @Test
    public void getError() {
        SampleModel model = createModel();

        SampleState state = model.getError(SampleState.create(SampleState.State.COMPLETE, "test",
                "data", ""), new Throwable("error"));

        assertThat(state, equalTo(SampleState.create(SampleState.State.ERROR, "test",
                "data", "error")));
    }

    @Test
    public void onConnectionChangedConnectedLoadingTest() {
        SampleModel model = createModel();

        SampleState state = model.onConnectionChanged(SampleState.create(SampleState.State.LOADING, "test",
                "", ""), true);

        assertThat(state, equalTo(SampleState.create(SampleState.State.LOADING, "test",
                "", "")));
    }

    @Test
    public void onConnectionChangedDisconnectedLoadingTest() {
        SampleModel model = createModel();

        SampleState state = model.onConnectionChanged(SampleState.create(SampleState.State.LOADING, "test",
                "", ""), false);

        assertThat(state, equalTo(SampleState.create(SampleState.State.ERROR, "test",
                "", "no connection")));
    }

    @Test
    public void onConnectionChangedConnectedErrorTest() {
        SampleModel model = createModel();

        SampleState state = model.onConnectionChanged(SampleState.create(SampleState.State.ERROR, "test",
                "", ""), true);

        assertThat(state, equalTo(SampleState.create(SampleState.State.COMPLETE, "test",
                "", "")));
    }

    @Test
    public void loadingStateTest() {
        SampleModel model = createModel();

        SampleState state = model.loadingState(SampleState.create(SampleState.State.COMPLETE, "test",
                "", ""));

        assertThat(state, equalTo(SampleState.create(SampleState.State.LOADING, "test",
                "", "")));
    }

    @Test
    public void getConnectionErrorTest() {
        SampleModel model = createModel();

        SampleState state = model.getConnectionError(SampleState.create(SampleState.State.COMPLETE, "test",
                "", ""));

        assertThat(state, equalTo(SampleState.create(SampleState.State.COMPLETE, "test",
                "", "no connection")));
    }

    @NonNull
    private SampleModel createModel() {
        return new SampleModel(sampleSource, connectionSource, executorsFactory, router);
    }
}
