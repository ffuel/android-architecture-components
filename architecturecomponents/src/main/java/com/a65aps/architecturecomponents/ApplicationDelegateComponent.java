package com.a65aps.architecturecomponents;

import android.app.Application;
import android.support.annotation.NonNull;

import dagger.BindsInstance;

public interface ApplicationDelegateComponent<T extends ApplicationDelegate> {

    void inject(@NonNull T delegate);

    interface Builder<T extends ApplicationDelegateComponent> {

        @BindsInstance
        @NonNull
        Builder application(@NonNull Application application);

        @NonNull
        T build();
    }

}
