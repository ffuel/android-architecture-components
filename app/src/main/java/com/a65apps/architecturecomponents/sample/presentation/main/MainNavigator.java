package com.a65apps.architecturecomponents.sample.presentation.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;
import com.a65apps.ciceronev4architecturecomponents.BasicNavigator;
import com.a65apps.ciceronev4architecturecomponents.FragmentAnimationFactory;
import com.a65apps.ciceronev4architecturecomponents.NavigationInterceptor;

import java.util.Map;

final class MainNavigator extends BasicNavigator {

    MainNavigator(@NonNull FragmentActivity activity,
                  @NonNull ContainerIdProvider idProvider,
                  @NonNull Map<String, NavigationInterceptor> interceptorMap,
                  @NonNull Map<String, FragmentAnimationFactory> animationMap) {
        super(activity, idProvider, interceptorMap, animationMap);
    }
}
