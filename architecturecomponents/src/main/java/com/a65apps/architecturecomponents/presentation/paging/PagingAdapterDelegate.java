package com.a65apps.architecturecomponents.presentation.paging;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import io.reactivex.Single;

public abstract class PagingAdapterDelegate<Item, S extends PageParcelable<Item>,
        VH extends RecyclerView.ViewHolder> {

    @NonNull
    private final DiffCallback<Item, S> diffCallback;
    private final boolean calculateMoves;

    @Nullable
    private S pageState;

    public PagingAdapterDelegate(@NonNull DiffCallback<Item, S> diffCallback, boolean calculateMoves) {
        this.diffCallback = diffCallback;
        this.calculateMoves = calculateMoves;
    }

    public final void onBindViewHolder(@NonNull VH holder, int position) {
        if (pageState == null) {
            return;
        }

        Item item = pageState.item(position - pageState.anchor());
        if (item != null) {
            onBindViewHolder(holder, item);
        } else {
            onBindPlaceholder(holder, position);
        }
    }

    public final int getItemCount() {
        if (pageState != null) {
            return pageState.totalCount();
        }

        return 0;
    }

    @NonNull
    public final UpdateStateResult updateStateBlocking(@NonNull S pageState) {
        DiffUtil.DiffResult result = null;
        if (this.pageState != null) {
            diffCallback.setStates(this.pageState, pageState);
            result = DiffUtil.calculateDiff(diffCallback, calculateMoves);
        }

        this.pageState = pageState;

        return UpdateStateResult.create(result);
    }

    @NonNull
    public final Single<UpdateStateResult> updateState(@NonNull final S pageState) {
        return Single.fromCallable(() -> updateStateBlocking(pageState));
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull Item item);

    protected abstract void onBindPlaceholder(@NonNull VH holder, int position);

    public abstract static class DiffCallback<Item, S extends PageParcelable<Item>>
            extends DiffUtil.Callback {
        @Nullable
        private S oldPageState;
        @Nullable
        private S newPageState;

        @Override
        public final int getOldListSize() {
            if (oldPageState == null) {
                return 0;
            }

            return oldPageState.totalCount();
        }

        @Override
        public final int getNewListSize() {
            if (newPageState == null) {
                return 0;
            }

            return newPageState.totalCount();
        }

        @Nullable
        protected Item getOldItem(int position) {
            if (oldPageState == null) {
                return null;
            }

            return oldPageState.item(position - oldPageState.anchor());
        }

        @Nullable
        protected Item getNewItem(int position) {
            if (newPageState == null) {
                return null;
            }

            return newPageState.item(position - newPageState.anchor());
        }

        void setStates(@NonNull S oldPageState,
                       @NonNull S newPageState) {
            this.oldPageState = oldPageState;
            this.newPageState = newPageState;
        }
    }

    public static final class UpdateStateResult {

        @Nullable
        private final DiffUtil.DiffResult result;

        @NonNull
        public static UpdateStateResult create(@Nullable DiffUtil.DiffResult result) {
            return new UpdateStateResult(result);
        }

        private UpdateStateResult(@Nullable DiffUtil.DiffResult result) {
            this.result = result;
        }

        @Nullable
        public DiffUtil.DiffResult getResult() {
            return result;
        }
    }
}
