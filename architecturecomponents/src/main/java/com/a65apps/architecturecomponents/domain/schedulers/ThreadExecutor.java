package com.a65apps.architecturecomponents.domain.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface ThreadExecutor {
    @NonNull
    Scheduler getScheduler();
}
