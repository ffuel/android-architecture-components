package com.a65apps.architecturecomponents.sample.domain.posts;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.paging.PagingConfig;
import com.a65apps.architecturecomponents.domain.paging.PagingModel;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

class PostsModel extends PagingModel<PostState, PostsState, Router> implements PostsInteractor {

    private static final int PAGE_SIZE = 20;

    @NonNull
    private final PostsSource source;
    @NonNull
    private final ThreadExecutor ioExecutor;
    @NonNull
    private final ApplicationLogger logger;

    @Inject
    PostsModel(@NonNull Router router, @NonNull PostsSource source,
               @NonNull ExecutorsFactory executors, @NonNull ApplicationLogger logger) {
        super(PagingConfig.builder(PAGE_SIZE).build(), PostsState.defaultState(), router,
                executors.getExecutor(SchedulerType.IO), executors.getExecutor(SchedulerType.UI));
        this.source = source;
        ioExecutor = executors.getExecutor(SchedulerType.IO);
        this.logger = logger;
    }

    @UiThread
    @Override
    protected void onZeroItems(int pageSize) {
        addDisposable(loadingPipeline(source.data(PostsRequest.create(0, pageSize, true)),
                0, 0, false)
                .subscribe());
    }

    @UiThread
    @Override
    protected void onLoad(int position, int pageSize, int lastBindPosition, int anchor,
                          boolean isAllDataLoaded) {
        addDisposable(loadingPipeline(source.data(PostsRequest.create(position, pageSize, false)),
                lastBindPosition, anchor, isAllDataLoaded)
                .subscribe());
    }

    @UiThread
    @Override
    protected void loading() {
        setState(getState().loading());
    }

    @UiThread
    @Override
    @NonNull
    protected Single<Integer> onBoundaryReached(int position, int pageSize) {
        return source.data(PostsRequest.create(position, pageSize, true))
                .onErrorReturn(this::onErrorPosts)
                .map(List::size);
    }

    @UiThread
    @Override
    protected boolean calculateEndData(int loadedSize) {
        return loadedSize < PAGE_SIZE - 1;
    }

    @NonNull
    private Single<Integer> loadingPipeline(@NonNull Single<List<PostState>> input,
                                            int lastBindPosition, int anchor, boolean isAllDataLoaded) {
        return input.subscribeOn(ioExecutor.getScheduler())
                .onErrorReturn(this::onErrorPosts)
                .flatMap(posts -> getCount(posts, lastBindPosition, anchor, isAllDataLoaded));
    }

    private void setPosts(@NonNull List<PostState> posts, int count, int lastBindPosition, int anchor,
                          boolean isAllDataLoaded) {
        setState(PostsState.newPosts(posts, count, lastBindPosition, anchor, isAllDataLoaded));
    }

    @NonNull
    private Single<Integer> getCount(@NonNull List<PostState> posts, int lastBindPosition, int anchor,
                                     boolean isAllDataLoaded) {
        return source.count()
                .onErrorReturn(this::onErrorCount)
                .doOnSuccess(count -> setPosts(posts, count, lastBindPosition, anchor, isAllDataLoaded));
    }

    private int onErrorCount(@NonNull Throwable error) {
        logger.logError(error);
        return getState().count();
    }

    @NonNull
    private List<PostState> onErrorPosts(@NonNull Throwable error) {
        logger.logError(error);
        return getState().items();
    }
}
