package com.a65apps.architecturecomponents;

import android.app.Application;
import android.support.annotation.Nullable;

public class SampleApplication extends Application {

    @Nullable
    private SampleApplicationDelegate delegate;

    @Override
    public void onCreate() {
        super.onCreate();
        delegate = SampleApplicationDelegate.create(this);
    }

    @Nullable
    public SampleApplicationDelegate getDelegate() {
        return delegate;
    }
}
