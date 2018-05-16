package com.a65apps.architecturecomponents.sample.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.preferences.PreferencesSource;
import com.a65apps.architecturecomponents.domain.source.SinglePutSource;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Single;

class SampleFakeDbSource implements SinglePutSource<String> {

    @NonNull
    private final PreferencesSource<String> preference;

    @Inject
    SampleFakeDbSource(@Named(PreferenceKeys.SAMPLE_DATA_KEY) @NonNull PreferencesSource<String> preference) {
        this.preference = preference;
    }

    @NonNull
    @Override
    public Single<String> data() {
        return preference.data();
    }

    @NonNull
    @Override
    public Completable putData(@NonNull final String data) {
        return preference.putData(data);
    }
}
