package com.a65apps.architecturecomponents;

import android.app.Application;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.ApplicationDelegate;

public final class SampleApplicationDelegate extends ApplicationDelegate<SampleComponent> {

    @NonNull
    private final Application application;

    private SampleApplicationDelegate(@NonNull Application application) {
        this.application = application;
    }

    @NonNull
    static SampleApplicationDelegate create(@NonNull Application application) {
        SampleApplicationDelegate delegate = new SampleApplicationDelegate(application);
        ApplicationDelegate.initDelegate(delegate);
        delegate.onCreate();

        return delegate;
    }

    @Override
    @NonNull
    protected SampleComponent initInjector() {
        return (SampleComponent) DaggerSampleComponent
                .builder()
                .application(application)
                .build();
    }
}
