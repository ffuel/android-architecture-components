package com.a65apps.architecturecomponents.domain.paging;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PagingModelTest {

    private static final int PAGE_SIZE = 20;

    @Mock
    private Router router;

    @Mock
    private PageState<Object> state;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private final ThreadExecutor executor = Schedulers::trampoline;

    @Test
    public void testFirstStartNoItems() {
        when(state.count()).thenReturn(0);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));
        model.firstStart(false);

        verify(model, times(1))
                .onZeroItems(eq(PAGE_SIZE));
    }

    @Test
    public void testFirstStartHasItems() {
        when(state.count()).thenReturn(PAGE_SIZE);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));
        model.firstStart(false);

        verify(model, times(0))
                .onZeroItems(anyInt());
    }

    @Test
    public void testBindForwardLoadingDefaultLoadingOffset() {
        when(state.totalCount()).thenReturn(40);
        when(state.item(eq(PAGE_SIZE))).thenReturn(null);
        when(state.lastBindPosition()).thenReturn(PAGE_SIZE - 1);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));

        model.bind(PAGE_SIZE, PAGE_SIZE - 5);

        verify(model, times(1))
                .onLoad(eq(0), eq(PAGE_SIZE + PAGE_SIZE), eq(PAGE_SIZE), eq(0), eq(false));
        verify(model, times(1))
                .loading();
    }

    @Test
    public void testBindForwardLoadingCustomLoadingOffset() {
        when(state.totalCount()).thenReturn(40);
        when(state.item(eq(PAGE_SIZE))).thenReturn(null);
        when(state.lastBindPosition()).thenReturn(PAGE_SIZE - 1);
        when(state.anchor()).thenReturn(0);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .withLoadingOffset(10)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));

        model.bind(PAGE_SIZE, PAGE_SIZE - 5);

        verify(model, times(1))
                .onLoad(eq(10), eq(PAGE_SIZE + 10), eq(PAGE_SIZE), eq(10), eq(false));
        verify(model, times(1))
                .loading();
        verify(state, times(1))
                .item(eq(20));
    }

    @Test
    public void testBindForwardLoadingCustomLoadingOffsetAnchor() {
        when(state.totalCount()).thenReturn(40);
        when(state.lastBindPosition()).thenReturn(PAGE_SIZE - 1);
        when(state.anchor()).thenReturn(10);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .withLoadingOffset(10)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));

        model.bind(PAGE_SIZE, PAGE_SIZE - 5);

        verify(model, times(1))
                .onLoad(eq(10), eq(PAGE_SIZE + 10), eq(PAGE_SIZE), eq(10), eq(false));
        verify(model, times(1))
                .loading();
        verify(state, times(1))
                .item(eq(10));
    }

    @Test
    public void testBindBackwardLoadingDefaultLoadingOffset() {
        when(state.totalCount()).thenReturn(40);
        when(state.item(eq(PAGE_SIZE - 5))).thenReturn(null);
        when(state.lastBindPosition()).thenReturn(PAGE_SIZE + 1);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));

        model.bind(PAGE_SIZE, PAGE_SIZE - 5);

        verify(model, times(1))
                .onLoad(eq(0), eq(PAGE_SIZE), eq(PAGE_SIZE), eq(0), eq(false));
        verify(model, times(1))
                .loading();
    }

    @Test
    public void testBindBackwardLoadingCustomLoadingOffset() {
        when(state.totalCount()).thenReturn(40);
        when(state.item(eq(33))).thenReturn(null);
        when(state.lastBindPosition()).thenReturn(39);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .withLoadingOffset(10)
                .build();

        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));

        model.bind(38, 33);

        verify(model, times(1))
                .onLoad(eq(8), eq(PAGE_SIZE + 10), eq(38), eq(8), eq(false));
        verify(model, times(1))
                .loading();
    }

    @Test
    public void testOnBoundaryReached() {
        when(state.totalCount()).thenReturn(40);
        PagingConfig config = PagingConfig.builder(PAGE_SIZE)
                .build();
        PagingModel<Object, PageState<Object>, Router> model = spy(new MockPageModel(config, state,
                router, executor));

        model.bind(39, 34);

        verify(model, times(1))
                .onBoundaryReached(eq(40), eq(PAGE_SIZE));
    }

    private static class MockPageModel extends PagingModel<Object, PageState<Object>, Router> {

        MockPageModel(@NonNull PagingConfig config, @NonNull PageState<Object> defaultState,
                      @NonNull Router router, @NonNull ThreadExecutor executor) {
            super(config, defaultState, router, executor, executor);
        }

        @Override
        protected void onZeroItems(int pageSize) {

        }

        @Override
        protected void onLoad(int position, int pageSize, int lastBindPosition, int anchor, boolean isAllDataLoaded) {

        }

        @Override
        protected void loading() {

        }


        @NonNull
        @Override
        protected Single<Integer> onBoundaryReached(int position, int pageSize) {
            return Single.just(PAGE_SIZE);
        }

        @Override
        protected boolean calculateEndData(int loadedSize) {
            return false;
        }
    }
}
