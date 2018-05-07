package com.a65aps.moxyarchitecturecomponents.view;

import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.view.CompositeStateView;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MoxyCompositeStateView<T extends State, S extends State>
        extends CompositeStateView<T, S>, MvpView {
}
