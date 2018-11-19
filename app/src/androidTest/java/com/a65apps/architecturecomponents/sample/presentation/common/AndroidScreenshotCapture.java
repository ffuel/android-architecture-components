package com.a65apps.architecturecomponents.sample.presentation.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class AndroidScreenshotCapture implements ScreenshotCapture {

    @Inject
    public AndroidScreenshotCapture() {
//      Inject constructor
    }

    @Nullable
    @Override
    public Data takeScreenshot(@NonNull Activity activity) {
        View view = activity.getWindow().getDecorView().getRootView();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        Data result = new Data(outputStream.toByteArray());
        bitmap.recycle();
        return result;
    }
}
