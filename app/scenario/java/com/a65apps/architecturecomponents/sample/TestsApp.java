package com.a65apps.architecturecomponents.sample;

import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class TestsApp extends SampleApplication {

    @Nullable
    private static TestsApp instance;

    private TestSampleComponent component;

    @Nullable
    public static TestsApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        //noinspection AccessStaticViaInstance
        this.instance = this;
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        component = (TestSampleComponent) DaggerTestSampleComponent.builder()
                .permissionsModule(new TestPermissionsModule(new PermissionsManager()))
                .create(this);
        return component;
    }

    public TestSampleComponent getComponent() {
        return component;
    }
}
