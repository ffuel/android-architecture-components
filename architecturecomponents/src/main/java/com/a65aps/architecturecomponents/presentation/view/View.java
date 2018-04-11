package com.a65aps.architecturecomponents.presentation.view;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.State;

@UiThread
public interface View<T extends State> {

    void updateState(@NonNull T state);
}
