package com.a65apps.architecturecomponents.sample;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.architecturecomponents.sample.presentation.main.MainPresenterTest;
import com.a65apps.architecturecomponents.sample.presentation.mvi.MviPresenterTest;
import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenterTest;
import com.a65apps.daggerarchitecturecomponents.PermissionsModule;
import com.a65apps.daggerarchitecturecomponents.source.Remote;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        TestApplicationModule.class
})
@Singleton
public interface TestApplicationComponent {

    SharedPreferences providesSharedPreferences();

    StringResources providesStringResources();

    ConnectionReceiverSource providesConnectionReceiverSource();

    @Remote
    SingleSource<String> providesSampleFakeNetworkSource();

    void inject(MainPresenterTest presenterTest);
    void inject(SamplePresenterTest presenterTest);
    void inject(MviPresenterTest presenterTest);

    @Component.Builder
    interface Builder {
        @NonNull
        Builder permissionsModule(@NonNull PermissionsModule module);

        TestApplicationComponent build();
    }
}
