package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.paging.RecyclerPagingDelegate;
import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsInteractor;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsState;
import com.a65apps.architecturecomponents.sample.presentation.common.ButterFragment;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class PostsFragment extends ButterFragment<PostsState, PostsParcelable, MoxyView<PostsState>,
        PostsInteractor, Router, PostsPresenter>
        implements MoxyView<PostsState>, RecyclerPagingDelegate.Parent, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    PostsPresenter presenter;
    @Inject
    ExecutorsFactory executors;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    private PostsAdapter adapter;
    @Nullable
    private LinearLayoutManager layoutManager;
    @Nullable
    private RecyclerView.OnScrollListener scrollListener;

    @NonNull
    public static Fragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new PostsAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        scrollListener = RecyclerPagingDelegate.attach(this, recyclerView);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        RecyclerPagingDelegate.detach(recyclerView, scrollListener);
        scrollListener = null;
        adapter = null;
        layoutManager = null;
        super.onDestroyView();
    }

    @Override
    protected void updateState(@NonNull PostsParcelable state) {
        if (layoutManager != null && state.isLoading()) {
            int firstItem = layoutManager.findFirstVisibleItemPosition();
            if (firstItem == RecyclerView.NO_POSITION || firstItem == 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (adapter != null && state.isDataChanged()) {
            adapter.updateStateBlocking(state);
        }

        if (!state.error().isEmpty()) {
            Snackbar.make(recyclerView, state.error(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_posts;
    }

    @NonNull
    @Override
    protected PostsPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    PostsPresenter providePostsPresenter() {
        return PresenterInjector.build(PostsPresenter.class, this);
    }

    @Override
    public int findLastVisibleItemPosition() {
        if (layoutManager != null) {
            return layoutManager.findLastVisibleItemPosition();
        }

        return 0;
    }

    @Override
    public int findFirstVisibleItemPosition() {
        if (layoutManager != null) {
            return layoutManager.findFirstVisibleItemPosition();
        }

        return 0;
    }

    @Override
    public void onBind(int lastPos, int firstPos) {
        presenter.bind(lastPos, firstPos);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }
}
