package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.data.permissions.AndroidPermissionsSource;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;

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
