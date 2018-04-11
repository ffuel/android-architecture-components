package com.a65apps.architecturecomponents;

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
    }
}
