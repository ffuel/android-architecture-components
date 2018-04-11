package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.data.receiver.ConnectionBroadcastReceiverSource;
import com.a65aps.architecturecomponents.domain.receiver.ConnectionReceiverSource;

import dagger.Binds;
import dagger.Module;

@Module
public interface ConnectionStateModule {

    @Binds
    @NonNull
    ConnectionReceiverSource bindsTo(@NonNull ConnectionBroadcastReceiverSource source);
}
