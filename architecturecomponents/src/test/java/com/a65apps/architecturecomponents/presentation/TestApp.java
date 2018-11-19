package com.a65apps.architecturecomponents.presentation;

import android.app.Application;

import com.a65apps.architecturecomponents.R;

public class TestApp extends Application {
    @Override
    public void onCreate() {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate();
    }
}
