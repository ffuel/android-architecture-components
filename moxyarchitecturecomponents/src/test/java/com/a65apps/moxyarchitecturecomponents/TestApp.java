package com.a65apps.moxyarchitecturecomponents;

import android.app.Application;

public class TestApp extends Application {
    @Override
    public void onCreate() {
        setTheme(com.a65apps.architecturecomponents.R.style.Theme_AppCompat);
        super.onCreate();
    }
}
