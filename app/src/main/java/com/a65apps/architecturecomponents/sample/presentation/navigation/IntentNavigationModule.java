package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.support.annotation.NonNull;

import com.a65apps.ciceronearchitecturecomponents.IntentFabric;

import java.util.Map;

import dagger.Module;
import dagger.multibindings.Multibinds;

@Module
public interface IntentNavigationModule {

    @Multibinds
    @NonNull
    Map<String, IntentFabric> multibindsIntentFabric();
}
