package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;
import com.a65apps.ciceronearchitecturecomponents.BasicNavigator;
import com.a65apps.ciceronearchitecturecomponents.FragmentAnimationFactory;
import com.a65apps.ciceronearchitecturecomponents.FragmentFactory;
import com.a65apps.ciceronearchitecturecomponents.IntentFactory;
import com.a65apps.ciceronearchitecturecomponents.NavigationInterceptor;

import java.util.Map;

public final class MainNavigator extends BasicNavigator {

    @NonNull
    private final FragmentActivity activity;

    MainNavigator(@NonNull FragmentActivity activity,
                  @NonNull ContainerIdProvider idProvider,
                  @NonNull Map<String, FragmentFactory> fragmentMap,
                  @NonNull Map<String, IntentFactory> intentMap,
                  @NonNull Map<String, NavigationInterceptor> interceptorMap,
                  @NonNull Map<String, FragmentAnimationFactory> animationMap) {
        super(activity, idProvider, fragmentMap, intentMap, interceptorMap, animationMap);
        this.activity = activity;
    }

    @Override
    protected void showSystemMessage(String message) {
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
