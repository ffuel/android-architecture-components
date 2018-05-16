package com.a65apps.architecturecomponents.presentation.schedulers;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class UiExecutorThread implements ThreadExecutor {
    @Inject
    public UiExecutorThread() {
        // @Inject constructor
    }

    @NonNull
    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
