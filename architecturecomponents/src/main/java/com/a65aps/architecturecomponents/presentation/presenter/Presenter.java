package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.view.View;

public interface Presenter<S extends State, V extends View<S>, I extends Interactor<S, R>,
        R extends Router> {

    @Nullable
    V getView();

    @NonNull
    R getRouter();

    @NonNull
    I getInteractor();

    @UiThread
    void restoreState(@NonNull S state);

    @UiThread
    void onBackPressed();

    void setTag(@Nullable Object tag);

    @Nullable
    Object getTag();
}
