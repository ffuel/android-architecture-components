package com.a65apps.architecturecomponents.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestConnectionStateModule {

    private final ConnectionReceiverSource source = Mockito.mock(ConnectionReceiverSource.class);

    @Provides
    @NonNull
    @Singleton
    ConnectionReceiverSource providesConnectionReceiverSource() {
        return source;
    }
}
