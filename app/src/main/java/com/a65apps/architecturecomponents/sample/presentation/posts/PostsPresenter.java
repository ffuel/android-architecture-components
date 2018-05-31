package com.a65apps.architecturecomponents.sample.presentation.posts;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.sample.domain.main.MainInteractor;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsInteractor;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsState;
import com.a65apps.moxyarchitecturecomponents.presenter.MoxyPresenter;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

@InjectViewState
public class PostsPresenter extends MoxyPresenter<PostsState, MoxyView<PostsState>,
        PostsInteractor, Router> {

    @NonNull
    private final MainInteractor mainInteractor;

    @Inject
    PostsPresenter(@NonNull ExecutorsFactory executors,
                   @NonNull PostsInteractor interactor,
                   @NonNull ApplicationLogger logger, @NonNull MainInteractor mainInteractor) {
        super(executors, interactor, logger);
        this.mainInteractor = mainInteractor;
    }

    @UiThread
    @Override
    public void onBackPressed() {
        mainInteractor.onBack();
    }

    @UiThread
    void bind(int lastPos, int firstPos) {
        getInteractor().bind(lastPos, firstPos);
    }

    @UiThread
    void onRefresh() {
        getInteractor().reload();
    }
}
