package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        implements MoxyView<PostsState>, RecyclerPagingDelegate.Parent {

    @InjectPresenter
    PostsPresenter presenter;
    @Inject
    ExecutorsFactory executors;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    private PostsAdapter adapter;
    @Nullable
    private LinearLayoutManager layoutManager;

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
        RecyclerPagingDelegate.attach(this, recyclerView);
    }

    @Override
    public void onDestroyView() {
        adapter = null;
        layoutManager = null;
        super.onDestroyView();
    }

    @Override
    protected void updateState(@NonNull PostsParcelable state) {
        if (adapter != null && state.isDataChanged()) {
            adapter.updateStateBlocking(state);
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
}
