package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;
import com.a65apps.ciceronearchitecturecomponents.BasicNavigator;
import com.a65apps.ciceronearchitecturecomponents.FragmentFabric;
import com.a65apps.ciceronearchitecturecomponents.IntentFabric;

import java.util.Map;

public final class MainNavigator extends BasicNavigator {

    @NonNull
    private final FragmentActivity activity;

    MainNavigator(@NonNull FragmentActivity activity,
                  @NonNull ContainerIdProvider idProvider,
                  @NonNull Map<String, FragmentFabric> fragmentMap,
                  @NonNull Map<String, IntentFabric> intentMap) {
        super(activity, idProvider, fragmentMap, intentMap);
        this.activity = activity;
    }

    @Override
    protected void showSystemMessage(String message) {
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
