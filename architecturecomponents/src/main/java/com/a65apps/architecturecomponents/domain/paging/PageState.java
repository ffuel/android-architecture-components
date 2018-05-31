package com.a65apps.architecturecomponents.domain.paging;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.State;

import java.util.List;

public abstract class PageState<T> implements State {

    /**
     * @param position position for Item.
     * @return Item for position. If Item is null we reached the page boundary.
     */
    @Nullable
    public T item(int position) {
        if (position >= 0 && position < items().size()) {
            return items().get(position);
        }

        return null;
    }

    public int totalCount() {
        return count() + (isAllDataLoaded() ? 0 : 1);
    }

    /**
     * @return count of all data
     */
    public abstract int count();

    public abstract int lastBindPosition();

    public abstract int anchor();

    public abstract boolean isLoading();

    public abstract boolean isDataChanged();

    public abstract boolean isAllDataLoaded();

    @NonNull
    public abstract List<T> items();
}
