package com.a65aps.architecturecomponents.presentation.view;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;

public interface CompositeStateView<T extends State, S extends State> extends View<T> {

    void updateDependentState(@NonNull S state);
}
