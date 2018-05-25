package com.a65apps.architecturecomponents.domain.paging;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.model.BaseModel;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import io.reactivex.Single;

public abstract class PagingModel<Item, S extends PageState<Item>, R extends Router> extends BaseModel<S, R>
        implements PagingInteractor<Item, S, R> {

    @NonNull
    private final PagingConfig config;
    @NonNull
    private final ThreadExecutor ioExecutor;
    @NonNull
    private final ThreadExecutor uiExecutor;

    public PagingModel(@NonNull PagingConfig config, @NonNull S defaultState,
                       @NonNull R router, @NonNull ThreadExecutor ioExecutor,
                       @NonNull ThreadExecutor uiExecutor) {
        super(defaultState, router);
        this.config = config;
        this.ioExecutor = ioExecutor;
        this.uiExecutor = uiExecutor;
    }

    @UiThread
    @Override
    public void firstStart(boolean isRestoring) {
        S state = getState();
        if (state.count() == 0) {
            onZeroItems(config.pageSize());
        }
    }

    @UiThread
    @Override
    public void bind(int lastPos, int firstPos) {
        S state = getState();
        if (state.isLoading() || lastPos == state.lastBindPosition()) {
            return;
        }

        if (!state.isAllDataLoaded()
                && lastPos >= state.totalCount() - 1 - config.getPageBoundaryOffset()) {
            loading();
            addDisposable(onBoundaryReached(lastPos + 1, config.pageSize())
                    .subscribeOn(ioExecutor.getScheduler())
                    .observeOn(uiExecutor.getScheduler())
                    .subscribe(size -> {
                        int offsetPosition = getOffsetPosition(lastPos);
                        onLoad(offsetPosition, config.pageSize() + config.pageLoadingOffset(),
                                lastPos, offsetPosition, calculateEndData(size));
                    }));
            return;
        }

        Item item;
        if (state.lastBindPosition() < lastPos) {
            item = state.item(lastPos - state.anchor());
        } else {
            item = state.item(firstPos - state.anchor());
        }
        if (item == null && state.lastBindPosition() < lastPos) {
            int offsetPosition = getOffsetPosition(lastPos);
            loading();
            onLoad(offsetPosition, config.pageSize() + config.pageLoadingOffset(),
                    lastPos, offsetPosition, state.isAllDataLoaded());
        } else if (item == null && state.lastBindPosition() > lastPos) {
            int size = config.pageSize() + config.pageLoadingOffset();
            int anchor = lastPos - size;
            if (anchor < 0) {
                size += anchor;
                anchor = 0;
            }
            loading();
            onLoad(anchor, size, lastPos, anchor, state.isAllDataLoaded());
        }
    }

    @UiThread
    protected abstract void onZeroItems(int pageSize);

    @UiThread
    protected abstract void onLoad(int position, int pageSize, int lastBindPosition, int anchor,
                                   boolean isAllDataLoaded);

    @UiThread
    protected abstract void loading();

    @UiThread
    @NonNull
    protected abstract Single<Integer> onBoundaryReached(int position, int pageSize);

    @UiThread
    protected abstract boolean calculateEndData(int loadedSize);

    private int getOffsetPosition(int lastPos) {
        int offsetPosition = lastPos - config.pageLoadingOffset();
        if (offsetPosition < 0) {
            offsetPosition = 0;
        }
        return offsetPosition;
    }
}
