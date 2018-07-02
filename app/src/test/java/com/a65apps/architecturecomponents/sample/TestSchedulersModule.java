package com.a65apps.architecturecomponents.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.daggerarchitecturecomponents.SchedulerKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface TestSchedulersModule {
    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.COMPUTATION)
    @NonNull
    ThreadExecutor bindsComputationExecutor(@NonNull TestThread executorThread);

    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.IO)
    @NonNull
    ThreadExecutor bindsIoExecutor(@NonNull TestThread executorThread);

    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.UI)
    @NonNull
    ThreadExecutor bindsUiExecutor(@NonNull TestThread executorThread);
}
