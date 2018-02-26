package com.a65aps.architecturecomponents.domain.schedulers;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface SchedulersModule {

    @Singleton
    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.IO)
    @NonNull
    ThreadExecutor bindIoThreadExecutor(IoExecutorThread executor);

    @Singleton
    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.COMPUTATION)
    @NonNull
    ThreadExecutor bindComputationThreadExecutor(ComputationExecutorThread executor);
}
