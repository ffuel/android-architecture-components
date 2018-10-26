package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;

public interface StateProvider<T extends State> {

    @NonNull
    T provide();
}
