package com.a65apps.architecturecomponents.presentation.paging;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PagingAdapterDelegateTest {

    @Mock
    private PagingAdapterDelegate.DiffCallback<Object, PageParcelable<Object>> callback;
    @Mock
    private RecyclerView.ViewHolder viewHolder;
    @Mock
    private PageParcelable<Object> page;
    @Mock
    private PageParcelable<Object> newPage;

    @NonNull
    private final Object item = new Object();

    private PagingAdapterDelegate<Object, PageParcelable<Object>, RecyclerView.ViewHolder> delegate;

    @Before
    public void setup() {
        delegate = spy(new MockDelegate(callback, false));
    }

    @Test
    public void testOnBindViewHolderEmptyPage() {
        delegate.onBindViewHolder(viewHolder, 0);

        verify(delegate, times(0))
                .onBindViewHolder(any(), any());
        verify(delegate, times(0))
                .onBindPlaceholder(any(), anyInt());
    }

    @Test
    public void testOnBindViewHolder() {
        when(page.item(eq(0))).thenReturn(item);

        DiffUtil.DiffResult result = delegate.updateStateBlocking(page).getResult();
        assertThat(result, equalTo(null));

        delegate.onBindViewHolder(viewHolder, 0);

        verify(delegate, times(1))
                .onBindViewHolder(eq(viewHolder), eq(item));
        verify(delegate, times(0))
                .onBindPlaceholder(any(), anyInt());
    }

    @Test
    public void testOnBindPlaceholder() {
        when(page.item(eq(5))).thenReturn(null);

        DiffUtil.DiffResult result = delegate.updateStateBlocking(page).getResult();
        assertThat(result, equalTo(null));

        delegate.onBindViewHolder(viewHolder, 5);

        verify(delegate, times(0))
                .onBindViewHolder(any(), any());
        verify(delegate, times(1))
                .onBindPlaceholder(eq(viewHolder), eq(5));
    }

    @Test
    public void testUpdateStateBlocking() {
        DiffUtil.DiffResult result = delegate.updateStateBlocking(page).getResult();
        assertThat(result, equalTo(null));
        result = delegate.updateStateBlocking(newPage).getResult();
        assertThat(result != null, equalTo(true));
    }

    @Test
    public void testUpdateState() {
        TestObserver<PagingAdapterDelegate.UpdateStateResult> observer = new TestObserver<>();
        delegate.updateState(page).subscribe(observer);
        DiffUtil.DiffResult result = observer.values().get(0).getResult();
        assertThat(result, equalTo(null));
        result = delegate.updateStateBlocking(newPage).getResult();
        assertThat(result != null, equalTo(true));
    }

    @Test
    public void testGetItemCount() {
        when(page.totalCount()).thenReturn(10);
        delegate.updateStateBlocking(page);

        assertThat(delegate.getItemCount(), equalTo(10));
    }

    private static class MockDelegate
            extends PagingAdapterDelegate<Object, PageParcelable<Object>, RecyclerView.ViewHolder> {
        MockDelegate(@NonNull DiffCallback<Object, PageParcelable<Object>> diffCallback,
                     boolean calculateMoves) {
            super(diffCallback, calculateMoves);
        }

        @Override
        protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {

        }

        @Override
        protected void onBindPlaceholder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }
    }
}
