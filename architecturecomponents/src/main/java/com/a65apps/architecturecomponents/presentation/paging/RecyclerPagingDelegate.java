package com.a65apps.architecturecomponents.presentation.paging;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public final class RecyclerPagingDelegate extends RecyclerView.OnScrollListener {

    @NonNull
    private final Parent parent;

    @NonNull
    public static RecyclerView.OnScrollListener attach(@NonNull Parent parent,
                              @NonNull RecyclerView recyclerView) {
        RecyclerPagingDelegate delegate = new RecyclerPagingDelegate(parent);
        recyclerView.addOnScrollListener(delegate);

        return delegate;
    }

    public static void detach(@NonNull RecyclerView recyclerView,
                              @Nullable RecyclerView.OnScrollListener listener) {
        recyclerView.removeOnScrollListener(listener);
    }

    private RecyclerPagingDelegate(@NonNull Parent parent) {
        this.parent = parent;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastPos = parent.findLastVisibleItemPosition();
        int firstPos = parent.findFirstVisibleItemPosition();
        parent.onBind(lastPos, firstPos);
    }

    public interface Parent {

        int findLastVisibleItemPosition();

        int findFirstVisibleItemPosition();

        void onBind(int lastPos, int firstPos);
    }
}
