package com.a65aps.architecturecomponents.domain.resources;

import android.support.annotation.NonNull;

public interface StringResources {

    @NonNull
    String getString(int id);

    @NonNull
    String getString(int id, @NonNull Object... args);
}
