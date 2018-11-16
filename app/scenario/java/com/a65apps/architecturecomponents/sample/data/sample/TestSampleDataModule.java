package com.a65apps.architecturecomponents.sample.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.preferences.PreferencesSource;
import com.a65apps.architecturecomponents.domain.source.SinglePutSource;
import com.a65apps.architecturecomponents.sample.domain.sample.SampleSource;
import com.a65apps.daggerarchitecturecomponents.source.Local;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = TestSampleNetworkModule.class)
public interface TestSampleDataModule {

    @Singleton
    @Binds
    @NonNull
    SampleSource bindsSampleDataSource(@NonNull SampleDataSource source);

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
