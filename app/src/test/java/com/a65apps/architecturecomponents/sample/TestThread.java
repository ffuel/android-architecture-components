package com.a65apps.architecturecomponents.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestThread implements ThreadExecutor {

    @Inject
    public TestThread() {
//      Inject constructor
    }

    @NonNull
    @Override
    public Scheduler getScheduler() {
        return Schedulers.trampoline();
    }
}
