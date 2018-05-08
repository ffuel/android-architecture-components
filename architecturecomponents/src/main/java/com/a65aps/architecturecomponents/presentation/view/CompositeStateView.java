package com.a65aps.architecturecomponents.presentation.view;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.State;

@UiThread
public interface CompositeStateView<T extends State, S extends State> extends View<T> {

    void updateDependentState(@NonNull S state);
}
