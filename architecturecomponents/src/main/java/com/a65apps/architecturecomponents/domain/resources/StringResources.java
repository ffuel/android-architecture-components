package com.a65apps.architecturecomponents.domain.resources;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.DataSource;

public interface StringResources extends DataSource {

    @NonNull
    String getString(int id);

    @NonNull
    String getString(int id, @NonNull Object... args);
}
