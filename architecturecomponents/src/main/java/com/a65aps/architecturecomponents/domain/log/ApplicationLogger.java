package com.a65aps.architecturecomponents.domain.log;

import android.support.annotation.NonNull;

public interface ApplicationLogger {

    void logError(@NonNull Throwable throwable);

    void logMessage(@NonNull String message);
}
