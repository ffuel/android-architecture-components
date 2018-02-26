package com.a65aps.architecturecomponents.data.log;

import android.support.annotation.NonNull;
import android.util.Log;

import com.a65aps.architecturecomponents.BuildConfig;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;

import javax.inject.Inject;

public final class AndroidApplicationLogger implements ApplicationLogger {

    private static final String TAG = AndroidApplicationLogger.class.getSimpleName();

    @Inject
    public AndroidApplicationLogger() {
        // @Inject constructor
    }

    @Override
    public void logError(@NonNull Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
    }

    @Override
    public void logMessage(@NonNull String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}
