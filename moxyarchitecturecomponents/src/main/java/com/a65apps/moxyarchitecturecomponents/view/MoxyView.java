package com.a65apps.moxyarchitecturecomponents.view;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.view.View;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MoxyView<S extends State> extends View<S>, MvpView {
}
