package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.data.common.SourceType;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsRequest;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostsDataSourceTest {

    @Mock
    private Map<SourceType, PostsSource> sources;
    @Mock
    private PostsSource network;
    @Mock
    private PostsSource db;

    @Before
    public void setup() {
        when(sources.get(eq(SourceType.NETWORK))).thenReturn(network);
        when(sources.get(eq(SourceType.DB))).thenReturn(db);
    }

    @Test
    public void countTest() {
        when(db.count()).thenReturn(Single.just(2));
        PostsDataSource source = createSource();
        TestObserver<Integer> observer = new TestObserver<>();

        source.count().subscribe(observer);

        observer.assertValue(2);
    }

    @Test
    public void dataForceNetworkTest() {
        when(network.data(eq(PostsRequest.create(0, 10, true))))
                .thenReturn(Single.just(Collections.emptyList()));
        when(db.putData(eq(Collections.emptyList())))
                .thenReturn(Completable.complete());
        when(db.data(eq(PostsRequest.create(0, 10, true))))
                .thenReturn(Single.just(Collections.emptyList()));
        PostsDataSource source = createSource();
        TestObserver<List<PostState>> observer = new TestObserver<>();

        source.data(PostsRequest.create(0, 10, true))
                .subscribe(observer);

        observer.assertValue(Collections.emptyList());
        verify(network, times(1))
                .data(eq(PostsRequest.create(0, 10, true)));
        verify(db, times(1))
                .putData(eq(Collections.emptyList()));
        verify(db, times(1))
                .data(eq(PostsRequest.create(0, 10, true)));
    }

    @Test
    public void dataNoNetworkTest() {
        when(db.data(eq(PostsRequest.create(0, 10, false))))
                .thenReturn(Single.just(Collections.emptyList()));
        PostsDataSource source = createSource();
        TestObserver<List<PostState>> observer = new TestObserver<>();

        source.data(PostsRequest.create(0, 10, false))
                .subscribe(observer);

        observer.assertValue(Collections.emptyList());
        verify(db, times(1))
                .data(eq(PostsRequest.create(0, 10, false)));
    }

    @Test
    public void putDataTest() {
        when(db.putData(eq(Collections.emptyList())))
                .thenReturn(Completable.complete());
        PostsDataSource source = createSource();

        source.putData(Collections.emptyList()).subscribe();

        verify(db, times(1))
                .putData(eq(Collections.emptyList()));
    }

    @NonNull
    private PostsDataSource createSource() {
        return new PostsDataSource(sources);
    }
}
