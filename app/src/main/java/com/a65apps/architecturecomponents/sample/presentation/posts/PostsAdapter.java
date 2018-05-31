package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a65apps.architecturecomponents.presentation.paging.PagingAdapter;
import com.a65apps.architecturecomponents.presentation.paging.PagingAdapterDelegate;
import com.a65apps.architecturecomponents.sample.R;

class PostsAdapter extends PagingAdapter<PostParcelable, PostsParcelable, PostsAdapter.PostViewHolder> {

    PostsAdapter() {
        super(new PagingAdapterDelegate.DiffCallback<PostParcelable, PostsParcelable>() {
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                PostParcelable oldItem = getOldItem(oldItemPosition);
                PostParcelable newItem = getNewItem(newItemPosition);
                return oldItem == null && newItem == null && oldItemPosition == newItemPosition
                        || oldItem != null && newItem != null && oldItem.name().equals(newItem.name());

            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                PostParcelable oldItem = getOldItem(oldItemPosition);
                PostParcelable newItem = getNewItem(newItemPosition);
                return oldItem == null && newItem == null && oldItemPosition == newItemPosition
                        || oldItem != null && newItem != null && oldItem.equals(newItem);
            }
        }, false);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, @NonNull PostParcelable postState) {
        holder.bindPost(postState);
    }

    @Override
    protected void onBindPlaceholder(@NonNull PostViewHolder holder, int position) {
        holder.bindPlaceholder();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false));
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView title;
        @NonNull
        private final TextView description;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }

        void bindPost(@NonNull PostParcelable postState) {
            title.setText(postState.displayName());
            description.setText(postState.shortDescription());
        }

        void bindPlaceholder() {
            title.setText(R.string.loading);
            description.setText(R.string.loading);
        }
    }
}
