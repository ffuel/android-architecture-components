package com.a65apps.architecturecomponents.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.resources.StringResources;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestResourcesModule {

    private StringResources stringResources = Mockito.mock(StringResources.class);

    @Provides
    @NonNull
    @Singleton
    StringResources providesStringResources() {
        return stringResources;
    }
}
