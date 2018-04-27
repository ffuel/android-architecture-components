package com.a65aps.architecturecomponents.domain.log;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

public interface ApplicationLogger extends DataSource {

    void logError(@NonNull Throwable throwable);

    void logMessage(@NonNull String message);
}
