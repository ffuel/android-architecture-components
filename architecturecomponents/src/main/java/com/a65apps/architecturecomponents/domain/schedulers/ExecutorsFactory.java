package com.a65apps.architecturecomponents.domain.schedulers;

import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;

public final class ExecutorsFactory {
    @NonNull
    private final Map<SchedulerType, ThreadExecutor> executors;

    @Inject
    public ExecutorsFactory(@NonNull Map<SchedulerType, ThreadExecutor> executors) {
        this.executors = executors;
    }

    @NonNull
    public ThreadExecutor getExecutor(@NonNull SchedulerType type) {
        ThreadExecutor result = executors.get(type);
        if (result == null) {
            throw new IllegalArgumentException("Wrong scheduler type " + type);
        }

        return result;
    }
}
