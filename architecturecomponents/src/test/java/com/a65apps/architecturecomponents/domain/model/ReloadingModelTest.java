package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionState;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReloadingModelTest {

    private static final ConnectionState CONNECTED =
            ConnectionState.builder()
                    .type(1)
                    .subtype(1)
                    .typeName("")
                    .subtypeName("")
                    .state(ConnectionState.State.CONNECTED)
                    .detailedState(ConnectionState.DetailedState.CONNECTED)
                    .reason("")
                    .extraInfo("")
                    .isFailover(false)
                    .isAvailable(true)
                    .isRoaming(false)
                    .build();

    private static final ConnectionState DISCONNECTED =
            ConnectionState.builder()
                    .type(1)
                    .subtype(1)
                    .typeName("")
                    .subtypeName("")
                    .state(ConnectionState.State.DISCONNECTED)
                    .detailedState(ConnectionState.DetailedState.DISCONNECTED)
                    .reason("")
                    .extraInfo("")
                    .isFailover(false)
                    .isAvailable(true)
                    .isRoaming(false)
                    .build();

    @Mock
    private ConnectionReceiverSource connectionSource;
    @Mock
    private ThreadExecutor threadExecutor;
    @Mock
    private ReloadingState state;
    @Mock
    private ReloadingState errorState;
    @Mock
    private ReloadingState loadingState;
    @Mock
    private ReloadingState connectionErrorState;
    @Mock
    private ReloadingState dataState;
    @Mock
    private ReloadingState noDataState;
    @Mock
    private Router router;
    @Mock
    private SingleSource<ReloadingState> source;
    @Mock
    private SingleSource<ReloadingState> cacheSource;
    private MockModel model;

    @Before
    public void setup() {
        when(threadExecutor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(dataState.isLoading()).thenReturn(false);
        when(dataState.hasData()).thenReturn(true);
        when(noDataState.isLoading()).thenReturn(false);
        when(noDataState.hasData()).thenReturn(false);
        when(loadingState.isLoading()).thenReturn(true);
        when(loadingState.hasData()).thenReturn(false);
        Mockito.reset(state);

        if (model != null) {
            model.dispose();
        }
        model = new MockModel(state, router, connectionSource, threadExecutor,
                source, cacheSource, errorState, loadingState, connectionErrorState);
        model = spy(model);
    }

    @Test
    public void testClearFirstStartHasInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(CONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED));
        when(source.data()).thenReturn(Single.just(dataState));

        // When
        model.firstStart(false);

        // Then
        verify(source, times(1)).data();
        verify(model, times(1)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(true));
        verify(model, times(1)).getData();
        verify(model, times(0)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(0)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(true));
    }

    @Test
    public void testClearFirstStartNoInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(DISCONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(cacheSource.data()).thenReturn(Single.just(noDataState));

        // When
        model.firstStart(false);

        // Then
        verify(cacheSource, times(1)).data();
        verify(model, times(1)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(false));
        verify(model, times(0)).getData();
        verify(model, times(1)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(1)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(false));
    }

    @Test
    public void testHasCacheFirstStartNoInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(DISCONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(cacheSource.data()).thenReturn(Single.just(dataState));

        // When
        model.firstStart(false);

        // Then
        verify(cacheSource, times(1)).data();
        verify(model, times(1)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(false));
        verify(model, times(0)).getData();
        verify(model, times(1)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(1)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(true));
    }

    @Test
    public void testClearFirstStartHasInternetDataError() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(CONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED));
        when(source.data()).thenReturn(Single.error(new Throwable()));

        // When
        model.firstStart(false);

        // Then
        verify(source, times(1)).data();
        verify(model, times(1)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(true));
        verify(model, times(1)).getData();
        verify(model, times(0)).tryGetDataCached();
        verify(model, times(1)).getError(any(), any());
        verify(model, times(0)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(false));
    }

    @Test
    public void testFirstStartNoInternetErrorCache() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(DISCONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(cacheSource.data()).thenReturn(Single.error(new Throwable()));

        // When
        model.firstStart(false);

        // Then
        verify(cacheSource, times(1)).data();
        verify(model, times(1)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(false));
        verify(model, times(0)).getData();
        verify(model, times(1)).tryGetDataCached();
        verify(model, times(1)).getError(any(), any());
        verify(model, times(1)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(false));
    }

    @Test
    public void testReloadHasInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(CONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED));
        when(source.data()).thenReturn(Single.just(dataState));

        // When
        model.firstStart(false);
        assertThat(model.getState().hasData(), equalTo(true));
        model.reload();

        // Then
        verify(source, times(2)).data();
        verify(model, times(2)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(true));
        verify(model, times(2)).getData();
        verify(model, times(0)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(0)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(true));
    }

    @Test
    public void testReloadNoInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(DISCONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(cacheSource.data()).thenReturn(Single.just(noDataState));

        // When
        model.firstStart(false);
        assertThat(model.getState().hasData(), equalTo(false));
        model.reload();

        // Then
        verify(cacheSource, times(2)).data();
        verify(model, times(2)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(false));
        verify(model, times(0)).getData();
        verify(model, times(2)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(2)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(false));
    }

    @Test
    public void testReloadNoInternetHasCache() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(DISCONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(cacheSource.data()).thenReturn(Single.just(dataState));

        // When
        model.firstStart(false);
        assertThat(model.getState().hasData(), equalTo(true));
        model.reload();

        // Then
        verify(cacheSource, times(2)).data();
        verify(model, times(2)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(false));
        verify(model, times(0)).getData();
        verify(model, times(2)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(2)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(true));
    }

    @Test
    public void testConnectionChangedNoInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.fromArray(CONNECTED, DISCONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED))
                .thenReturn(Single.just(DISCONNECTED));
        when(source.data()).thenReturn(Single.just(dataState));

        // When
        model.firstStart(false);

        // Then
        verify(source, times(1)).data();
        verify(model, times(1)).loadingState(any());
        verify(model, times(2))
                .onConnectionChanged(any(), anyBoolean());
        verify(model, times(1)).getData();
        verify(model, times(0)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(0)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(true));
    }

    @Test
    public void testConnectionChangedHasInternet() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.fromArray(DISCONNECTED, CONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED))
                .thenReturn(Single.just(CONNECTED));
        when(cacheSource.data()).thenReturn(Single.just(noDataState));
        when(source.data()).thenReturn(Single.just(dataState));

        // When
        model.firstStart(false);

        // Then
        verify(cacheSource, times(1)).data();
        verify(source, times(1)).data();
        verify(model, times(2)).loadingState(any());
        verify(model, times(2))
                .onConnectionChanged(any(), anyBoolean());
        verify(model, times(1)).getData();
        verify(model, times(1)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(1)).getConnectionError(any());

        assertThat(model.getState().hasData(), equalTo(true));
    }

    @Test
    public void testRestoreWhenLoadingNotFinished() {
        // Given
        when(connectionSource.observeReceiver())
                .thenReturn(Observable.just(CONNECTED));
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED));
        when(source.data()).thenReturn(Single.just(loadingState));

        // When
        model.firstStart(false);
        model.restoreState(loadingState);

        // Then
        verify(source, times(2)).data();
        verify(model, times(2)).loadingState(any());
        verify(model, times(1))
                .onConnectionChanged(any(), eq(true));
        verify(model, times(2)).getData();
        verify(model, times(0)).tryGetDataCached();
        verify(model, times(0)).getError(any(), any());
        verify(model, times(0)).getConnectionError(any());
        verify(model, times(1)).reload();

        assertThat(model.getState().isLoading(), equalTo(true));
    }

    private static class MockModel extends ReloadingModel<ReloadingState, Router> {

        @NonNull
        private final SingleSource<ReloadingState> source;
        @NonNull
        private final SingleSource<ReloadingState> cacheSource;
        @NonNull
        private final ReloadingState errorState;
        @NonNull
        private final ReloadingState loadingState;
        @NonNull
        private final ReloadingState connectionErrorState;

        MockModel(@NonNull ReloadingState defaultState,
                         @NonNull Router router,
                         @NonNull ConnectionReceiverSource connectionSource,
                         @NonNull ThreadExecutor executor,
                         @NonNull SingleSource<ReloadingState> source,
                         @NonNull SingleSource<ReloadingState> cacheSource,
                         @NonNull ReloadingState errorState,
                         @NonNull ReloadingState loadingState,
                         @NonNull ReloadingState connectionErrorState) {
            super(defaultState, router, connectionSource, executor);
            this.source = source;
            this.cacheSource = cacheSource;
            this.errorState = errorState;
            this.loadingState = loadingState;
            this.connectionErrorState = connectionErrorState;
        }

        @NonNull
        @Override
        protected Single<ReloadingState> getData() {
            return source.data();
        }

        @NonNull
        @Override
        protected Single<ReloadingState> tryGetDataCached() {
            return cacheSource.data();
        }

        @NonNull
        @Override
        protected ReloadingState getError(@NonNull ReloadingState lastState,
                                          @NonNull Throwable error) {
            return errorState;
        }

        @NonNull
        @Override
        protected ReloadingState onConnectionChanged(@NonNull ReloadingState lastState,
                                                     boolean isConnected) {
            return new ReloadingState() {
                @Override
                public boolean hasData() {
                    return lastState.hasData();
                }

                @Override
                public boolean isLoading() {
                    return lastState.isLoading();
                }
            };
        }

        @NonNull
        @Override
        protected ReloadingState loadingState(@NonNull ReloadingState lastState) {
            return loadingState;
        }

        @NonNull
        @Override
        protected ReloadingState getConnectionError(@NonNull ReloadingState lastState) {
            return connectionErrorState;
        }
    }
}
