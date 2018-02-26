package com.a65aps.architecturecomponents.domain;


import com.a65aps.architecturecomponents.domain.schedulers.SchedulersModule;

import dagger.Module;

@Module(includes = {
        SchedulersModule.class
})
public interface DomainModule {
}
