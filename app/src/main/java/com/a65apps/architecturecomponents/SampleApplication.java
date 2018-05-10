package com.a65apps.architecturecomponents;

import com.a65aps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65aps.daggerarchitecturecomponents.DaggerApplication;
import com.a65aps.daggerarchitecturecomponents.PermissionsModule;

import dagger.android.AndroidInjector;

public class SampleApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends dagger.android.DaggerApplication> applicationInjector() {
        return com.a65apps.architecturecomponents.DaggerSampleComponent.builder()
                .permissionsModule(new PermissionsModule(new PermissionsManager()))
                .create(this);
    }
}
