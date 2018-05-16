package com.a65apps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.data.permissions.AndroidPermissionsSource;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;

import dagger.Module;
import dagger.Provides;

@Module
public class PermissionsModule {

    @NonNull
    private final PermissionsManager permissionsManager;

    public PermissionsModule(@NonNull PermissionsManager permissionsManager) {
        this.permissionsManager = permissionsManager;
    }

    @Provides
    @NonNull
    PermissionsSource providesPermissionsSource(@NonNull AndroidPermissionsSource source) {
        return source;
    }

    @Provides
    @NonNull
    PermissionsManager providesPermissionsManager() {
        return permissionsManager;
    }
}
