package com.a65aps.architecturecomponents.data.resources;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.resources.StringResources;

import javax.inject.Inject;

public class AndroidStringResources implements StringResources {

    @NonNull
    private final Resources resources;

    @Inject
    public AndroidStringResources(@NonNull Context context) {
        this.resources = context.getResources();
    }

    @NonNull
    @Override
    public String getString(int id) {
        return resources.getString(id);
    }

    @NonNull
    @Override
    public String getString(int id, @NonNull Object... args) {
        return resources.getString(id, args);
    }
}
