package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsRequest;

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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostsDataBaseSourceTest {

    private static final List<PostDb> POSTS = new ArrayList<PostDb>() {{
        add(new PostDb("name",
                "displayName",
                "shortDescription",
                "description",
                "createdBy",
                1.0,
                true,
                true));
    }};

    @Mock
    private PostsDataBase dataBase;
    @Mock
    private PostDao postDao;
    @Mock
    private Mapper<PostDb, PostState> dbToStateMapper;
    @Mock
    private Mapper<PostState, PostDb> stateToDbMapper;

    @Before
    public void setup() {
        when(dataBase.getPostDao()).thenReturn(postDao);
    }

    @Test
    public void countTest() {
        when(postDao.count()).thenReturn(Single.just(2));
        PostsDataBaseSource source = createSource();
        TestObserver<Integer> observer = new TestObserver<>();

        source.count().subscribe(observer);

        observer.assertValue(2);
    }

    @Test
    public void dataTest() {
        when(postDao.getPosts(eq(0), eq(10)))
                .thenReturn(Single.just(POSTS));
        when(dbToStateMapper.map(anyList()))
                .thenReturn(Collections.emptyList());
        PostsDataBaseSource source = createSource();

        source.data(PostsRequest.create(0, 10, false))
                .subscribe();

        verify(postDao, times(1))
                .getPosts(eq(0), eq(10));
        verify(dbToStateMapper, times(1))
                .map(eq(POSTS));
    }

    @Test
    public void putDataTest() {
        when(stateToDbMapper.map(anyList()))
                .thenReturn(Collections.emptyList());
        PostsDataBaseSource source = createSource();

        source.putData(Collections.emptyList()).subscribe();

        verify(postDao, times(1))
                .insert();
    }

    @NonNull
    private PostsDataBaseSource createSource() {
        return new PostsDataBaseSource(dataBase, dbToStateMapper, stateToDbMapper);
    }
}
