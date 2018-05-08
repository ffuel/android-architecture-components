package com.a65aps.daggerarchitecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.data.permissions.AndroidPermissionsSource;
import com.a65aps.architecturecomponents.domain.permissions.PermissionsSource;

import dagger.Binds;
import dagger.Module;

@Module
public interface PermissionsModule {

    @Binds
    @NonNull
    PermissionsSource bindsTo(@NonNull AndroidPermissionsSource source);
}
