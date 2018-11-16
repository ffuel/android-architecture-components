package com.a65apps.architecturecomponents.sample.presentation.common;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ScreenshotCapture {

    @Nullable
    Data takeScreenshot(@NonNull Activity activity);

    class Data {
        public final byte[] data;

        public Data(@NonNull byte[] data) {
            this.data = data;
        }
    }
}
