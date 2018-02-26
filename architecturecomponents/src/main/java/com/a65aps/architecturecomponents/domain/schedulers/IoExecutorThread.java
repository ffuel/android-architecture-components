package com.a65aps.architecturecomponents.domain.schedulers;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public final class IoExecutorThread implements ThreadExecutor {
    @Inject
    public IoExecutorThread() {
        // @Inject constructor
    }

    @NonNull
    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
