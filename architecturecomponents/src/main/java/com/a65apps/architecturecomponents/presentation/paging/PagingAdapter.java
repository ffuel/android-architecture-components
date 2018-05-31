package com.a65apps.architecturecomponents.presentation.paging;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import io.reactivex.Single;

public abstract class PagingAdapter<Item, S extends PageParcelable<Item>, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    @NonNull
    private final PagingAdapterDelegate<Item, S, VH> delegate;

    public PagingAdapter(@NonNull PagingAdapterDelegate.DiffCallback<Item, S> diffCallback,
                         boolean calculateMoves) {
        delegate = new PagingAdapterDelegate<Item, S, VH>(diffCallback, calculateMoves) {
            @Override
            protected void onBindViewHolder(@NonNull VH holder, @NonNull Item item) {
                PagingAdapter.this.onBindViewHolder(holder, item);
            }

            @Override
            protected void onBindPlaceholder(@NonNull VH holder, int position) {
                PagingAdapter.this.onBindPlaceholder(holder, position);
            }
        };
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        delegate.onBindViewHolder(holder, position);
    }

    @Override
    public final int getItemCount() {
        return delegate.getItemCount();
    }

    public final void updateStateBlocking(@NonNull S pageState) {
        DiffUtil.DiffResult result = delegate.updateStateBlocking(pageState).getResult();
        if (result != null) {
            result.dispatchUpdatesTo(this);
        } else {
            notifyDataSetChanged();
        }
    }

    @NonNull
    public final Single<PagingAdapterDelegate.UpdateStateResult> updateState(@NonNull final S pageState) {
        return delegate.updateState(pageState);
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull Item item);

    protected abstract void onBindPlaceholder(@NonNull VH holder, int position);
}
