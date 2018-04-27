package com.a65apps.architecturecomponents;

import com.a65aps.daggerarchitecturecomponents.DaggerApplication;

import dagger.android.AndroidInjector;

public class SampleApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends dagger.android.DaggerApplication> applicationInjector() {
        return com.a65apps.architecturecomponents.DaggerSampleComponent.builder().create(this);
    }
}
