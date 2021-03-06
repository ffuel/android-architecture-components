package com.a65apps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.schedulers.ComputationExecutorThread;
import com.a65apps.architecturecomponents.domain.schedulers.IoExecutorThread;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.schedulers.UiExecutorThread;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.Multibinds;

@Module
public interface SchedulersModule {

    @Multibinds
    @NonNull
    Map<SchedulerType, ThreadExecutor> multibindsThreadExecutor();

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
