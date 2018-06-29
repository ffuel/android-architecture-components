package com.a65apps.architecturecomponents.sample.domain.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionState;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostsModelTest {

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
    private static final ConnectionState DISCONNECTED = ConnectionState.builder()
            .type(1)
            .detailedState(ConnectionState.DetailedState.DISCONNECTED)
            .extraInfo("")
            .isAvailable(false)
            .isFailover(false)
            .isRoaming(false)
            .reason("")
            .state(ConnectionState.State.DISCONNECTED)
            .subtype(1)
            .subtypeName("")
            .typeName("")
            .build();

    private static final List<PostState> SUCCESS;
    static {
        SUCCESS = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            SUCCESS.add(PostState.builder()
                    .shortDescription("short" + i)
                    .score(1.0)
                    .name("name" + i)
                    .featured(true)
                    .displayName("name display" + i)
                    .description("description" + i)
                    .curated(true)
                    .createdBy("createdBy" + i)
                    .build());
        }
    }

    private static final PostsRequest REQUEST_CONNECTED = PostsRequest.create(0,
            20, true);

    private static final PostsRequest REQUEST_DISCONNECTED = PostsRequest.create(0,
            20, false);

    @Mock
    private Router router;
    @Mock
    private PostsSource source;
    @Mock
    private ExecutorsFactory executors;
    @Mock
    private ApplicationLogger logger;
    @Mock
    private ConnectionReceiverSource connectionSource;
    @Mock
    private StringResources stringResources;
    @Mock
    private ThreadExecutor executor;

    @Before
    public void setup() {
        when(executors.getExecutor(eq(SchedulerType.IO)))
                .thenReturn(executor);
    }

    @Test
    public void testConstructor() {
        PostsModel model = createModel();
        TestObserver<PostsState> observer = new TestObserver<>();

        model.observeState().subscribe(observer);

        observer.assertValue(PostsState.defaultState());
    }

    @Test
    public void reloadConnectedTest() {
        when(connectionSource.single())
                .thenReturn(Single.just(CONNECTED));
        when(source.data(eq(REQUEST_CONNECTED)))
                .thenReturn(Single.just(SUCCESS));
        when(source.count())
                .thenReturn(Single.just(40));
        when(executor.getScheduler()).thenReturn(Schedulers.trampoline());
        PostsModel model = createModel();
        TestObserver<PostsState> observer = new TestObserver<>();

        model.reload();
        model.observeState().subscribe(observer);

        observer.assertValue(PostsState.newPosts(SUCCESS, 40,
                0, 0, false));
    }

    @Test
    public void reloadDisconnectedHasCacheTest() {
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(source.data(eq(REQUEST_DISCONNECTED)))
                .thenReturn(Single.just(SUCCESS));
        when(source.count())
                .thenReturn(Single.just(40));
        when(executor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(stringResources.getString(anyInt())).thenReturn("error");
        PostsModel model = createModel();
        TestObserver<PostsState> observer = new TestObserver<>();

        model.reload();
        model.observeState().subscribe(observer);

        observer.assertValue(PostsState.newPosts(SUCCESS, 40,
                0, 0, false));
    }

    @Test
    public void reloadDisconnectedNoCacheTest() {
        when(connectionSource.single())
                .thenReturn(Single.just(DISCONNECTED));
        when(source.data(eq(REQUEST_DISCONNECTED)))
                .thenReturn(Single.just(Collections.emptyList()));
        when(source.count())
                .thenReturn(Single.just(0));
        when(executor.getScheduler()).thenReturn(Schedulers.trampoline());
        when(stringResources.getString(anyInt())).thenReturn("error");
        PostsModel model = createModel();
        TestObserver<PostsState> observer = new TestObserver<>();

        model.reload();
        model.observeState().subscribe(observer);

        observer.assertValue(PostsState.builder()
                .error("")
                .items(Collections.emptyList())
                .isAllDataLoaded(false)
                .anchor(0)
                .count(0)
                .lastBindPosition(0)
                .isLoading(false)
                .isDataChanged(true)
                .build());
    }

    @NonNull
    private PostsModel createModel() {
        return new PostsModel(router, source, executors, logger, connectionSource,
                stringResources);
    }
}
