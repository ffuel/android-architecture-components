package com.a65apps.architecturecomponents.sample.presentation.common;

import android.app.Activity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class StubScreenshotCapture implements ScreenshotCapture {

    @Inject
    public StubScreenshotCapture() {
//      Inject constructor
    }

    @Nullable
    @Override
    public Data takeScreenshot(@NonNull Activity activity) {
        return null;
    }
}
