package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.schedulers.ComputationExecutorThread;
import com.a65aps.architecturecomponents.domain.schedulers.IoExecutorThread;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.presentation.schedulers.UiExecutorThread;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface SchedulersModule {

    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.COMPUTATION)
    @NonNull
    ThreadExecutor bindsComputationExecutor(@NonNull ComputationExecutorThread executorThread);

    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.IO)
    @NonNull
    ThreadExecutor bindsIoExecutor(@NonNull IoExecutorThread executorThread);

    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.UI)
    @NonNull
    ThreadExecutor bindsUiExecutor(@NonNull UiExecutorThread executorThread);
}
