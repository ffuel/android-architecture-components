package com.a65apps.architecturecomponents.data.resources;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.resources.StringResources;

import net.jcip.annotations.NotThreadSafe;

import javax.inject.Inject;

@NotThreadSafe
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


    @NonNull
    @Override
    public String getQuantityString(int id, int quantity) {
        return resources.getQuantityString(id, quantity);
    }

    @NonNull
    @Override
    public String getQuantityString(int id, int quantity, @NonNull Object... formatArgs) {
        return resources.getQuantityString(id, quantity, formatArgs);
    }
}
