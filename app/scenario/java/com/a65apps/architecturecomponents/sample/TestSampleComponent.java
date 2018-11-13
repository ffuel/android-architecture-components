package com.a65apps.architecturecomponents.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.presentation.sample.scenario.SampleGivenState;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Component(modules = {
        TestSampleModule.class
})
@Singleton
public interface TestSampleComponent extends AndroidInjector<TestsApp> {

    void inject(@NonNull SampleGivenState sampleGivenState);

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TestsApp> {
        @NonNull
        public abstract Builder permissionsModule(@NonNull PermissionsModule module);

        public abstract TestSampleComponent build();
    }
}
