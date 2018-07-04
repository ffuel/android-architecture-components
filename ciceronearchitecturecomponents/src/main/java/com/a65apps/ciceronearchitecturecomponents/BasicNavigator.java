package com.a65apps.ciceronearchitecturecomponents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;

import java.util.Map;

import ru.terrakok.cicerone.android.SupportAppNavigator;

public class BasicNavigator extends SupportAppNavigator {

    @NonNull
    private final Map<String, FragmentFabric> fragmentMap;
    @NonNull
    private final Map<String, IntentFabric> intentMap;

    public BasicNavigator(@NonNull FragmentActivity activity,
                          @NonNull ContainerIdProvider idProvider,
                          @NonNull Map<String, FragmentFabric> fragmentMap,
                          @NonNull Map<String, IntentFabric> intentMap) {
        super(activity, idProvider.get());
        this.fragmentMap = fragmentMap;
        this.intentMap = intentMap;
    }

    @Override
    @Nullable
    protected Intent createActivityIntent(@NonNull Context context, @NonNull String screenKey,
                                          @Nullable Object data) {
        IntentFabric fabric = intentMap.get(screenKey);
        if (fabric != null) {
            return fabric.build(context, new Bundle(), data);
        }

        return null;
    }

    @Override
    @NonNull
    protected Fragment createFragment(@NonNull String screenKey, @Nullable Object data) {
        FragmentFabric fabric = fragmentMap.get(screenKey);
        if (fabric == null) {
            throw new IllegalArgumentException("Unknown screen key: " + screenKey);
        }

        return fabric.build(new Bundle(), data);
    }
}
