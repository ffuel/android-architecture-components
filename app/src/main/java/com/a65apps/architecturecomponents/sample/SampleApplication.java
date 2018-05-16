package com.a65apps.architecturecomponents.sample;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.daggerarchitecturecomponents.DaggerApplication;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;

import dagger.android.AndroidInjector;

public class SampleApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends dagger.android.DaggerApplication> applicationInjector() {
        return com.a65apps.architecturecomponents.sample.DaggerSampleComponent.builder()
                .permissionsModule(new PermissionsModule(new PermissionsManager()))
                .create(this);
    }
}
