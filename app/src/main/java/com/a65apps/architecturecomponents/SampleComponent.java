package com.a65apps.architecturecomponents;

import android.support.annotation.NonNull;

import com.a65aps.daggerarchitecturecomponents.PermissionsModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Component(modules = {
        ApplicationModule.class
})
@Singleton
public interface SampleComponent extends AndroidInjector<SampleApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleApplication> {
        @NonNull
        public abstract Builder permissionsModule(@NonNull PermissionsModule module);
    }
}
