package com.a65apps.architecturecomponents.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.sample.SampleSource;
import com.a65aps.architecturecomponents.domain.preferences.PreferencesSource;
import com.a65aps.architecturecomponents.domain.source.SinglePutSource;
import com.a65aps.architecturecomponents.domain.source.SingleSource;
import com.a65aps.daggerarchitecturecomponents.source.Local;
import com.a65aps.daggerarchitecturecomponents.source.Remote;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface SampleDataModule {

    @Singleton
    @Binds
    @NonNull
    SampleSource bindsSampleDataSource(@NonNull SampleDataSource source);

    @Singleton
    @Binds
    @Remote
    @NonNull
    SingleSource<String> bindsSampleFakeNetworkSource(@NonNull SampleFakeNetworkSource source);

    @Singleton
    @Binds
    @Local
    @NonNull
    SinglePutSource<String> bindsSampleFakeDbSource(@NonNull SampleFakeDbSource source);

    @Singleton
    @Binds
    @Named(PreferenceKeys.SAMPLE_DATA_KEY)
    @NonNull
    PreferencesSource<String> bindsSampleSharedPref(@NonNull SampleDataPreference preference);
}
