package com.a65apps.architecturecomponents.domain.paging;

import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

public interface PagingInteractor<Item, T extends PageState<Item>, R extends Router>
        extends Interactor<T, R> {

    @UiThread
    void bind(int lastPos, int firstPos);
}
