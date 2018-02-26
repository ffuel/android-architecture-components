package com.a65aps.architecturecomponents.presentation.view;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface BaseView<T extends State> extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateState(@NonNull T state);
}
