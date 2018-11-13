package com.a65apps.architecturecomponents.sample.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.daggerarchitecturecomponents.source.Remote;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestSampleNetworkModule {

    @SuppressWarnings("unchecked")
    private final SingleSource<String> source = Mockito.mock(SingleSource.class);

    @Singleton
    @Provides
    @Remote
    @NonNull
    SingleSource<String> providesSampleFakeNetworkSource() {
        return source;
    }

}
