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
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class BasicNavigator extends SupportAppNavigator {

    @NonNull
    private final Map<String, FragmentFabric> fragmentMap;
    @NonNull
    private final Map<String, IntentFabric> intentMap;
    @NonNull
    private final Map<String, NavigationInterceptor> interceptorMap;
    @NonNull
    private final FragmentActivity activity;

    public BasicNavigator(@NonNull FragmentActivity activity,
                          @NonNull ContainerIdProvider idProvider,
                          @NonNull Map<String, FragmentFabric> fragmentMap,
                          @NonNull Map<String, IntentFabric> intentMap,
                          @NonNull Map<String, NavigationInterceptor> interceptorMap) {
        super(activity, idProvider.get());
        this.fragmentMap = fragmentMap;
        this.intentMap = intentMap;
        this.interceptorMap = interceptorMap;
        this.activity = activity;
    }

    @Override
    protected void applyCommand(Command command) {
        String key = null;
        if (command instanceof BackTo) {
            key = ((BackTo) command).getScreenKey();
        }
        if (command instanceof Forward) {
            key = ((Forward) command).getScreenKey();
        }
        if (command instanceof Replace) {
            key = ((Replace) command).getScreenKey();
        }
        if (key != null) {
            NavigationInterceptor interceptor = interceptorMap.get(key);
            if (interceptor != null && interceptor.intercept(activity, command)) {
                return;
            }
        }

        super.applyCommand(command);
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
