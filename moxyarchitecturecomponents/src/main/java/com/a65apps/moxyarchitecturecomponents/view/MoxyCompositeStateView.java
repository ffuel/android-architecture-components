package com.a65apps.moxyarchitecturecomponents.view;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.view.CompositeStateView;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MoxyCompositeStateView<T extends State, S extends State>
        extends MvpView, CompositeStateView<T, S> {
}
