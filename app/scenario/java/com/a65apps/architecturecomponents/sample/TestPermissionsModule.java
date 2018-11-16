package com.a65apps.architecturecomponents.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestPermissionsModule {

    @NonNull
    private final PermissionsManager permissionsManager;
    @NonNull
    private final PermissionsSource source = Mockito.mock(PermissionsSource.class);

    public TestPermissionsModule(@NonNull PermissionsManager permissionsManager) {
        this.permissionsManager = permissionsManager;
    }

    @Provides
    @NonNull
    PermissionsSource providesPermissionsSource() {
        return source;
    }

    @Provides
    @NonNull
    PermissionsManager providesPermissionsManager() {
        return permissionsManager;
    }
}
