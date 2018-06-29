package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostsNetworkSourceTest {

    private static final PostsJson JSON = PostsJson.builder()
            .totalCount(0)
            .incompleteResults(false)
            .items(Collections.emptyList())
            .build();

    @Mock
    private PostsApi api;
    @Mock
    private Mapper<PostsJson, List<PostState>> mapper;

    @Test
    public void dataPage1Test() {
        when(api.posts(anyInt(), anyInt()))
                .thenReturn(Single.just(JSON));
        when(mapper.map(eq(JSON))).thenReturn(Collections.emptyList());
        PostsNetworkSource source = createSource();
        TestObserver<List<PostState>> observer = new TestObserver<>();

        source.data(PostsRequest.create(0, 20, true))
                .subscribe(observer);

        observer.assertValue(Collections.emptyList());
        verify(api, times(1))
                .posts(eq(1), eq(20));
    }

    @Test
    public void dataPage2Test() {
        when(api.posts(anyInt(), anyInt()))
                .thenReturn(Single.just(JSON));
        when(mapper.map(eq(JSON))).thenReturn(Collections.emptyList());
        PostsNetworkSource source = createSource();
        TestObserver<List<PostState>> observer = new TestObserver<>();

        source.data(PostsRequest.create(21, 20, true))
                .subscribe(observer);

        observer.assertValue(Collections.emptyList());
        verify(api, times(1))
                .posts(eq(2), eq(20));
    }

    @Test
    public void countTest() {
        PostsNetworkSource source = createSource();
        TestObserver<Integer> observer = new TestObserver<>();

        source.count().subscribe(observer);

        observer.assertError(UnsupportedOperationException.class);
    }

    @Test
    public void putDataTest() {
        PostsNetworkSource source = createSource();
        TestObserver observer = new TestObserver();

        source.putData(Collections.emptyList()).subscribe(observer);

        observer.assertError(UnsupportedOperationException.class);
    }

    @NonNull
    private PostsNetworkSource createSource() {
        return new PostsNetworkSource(api, mapper);
    }
}
