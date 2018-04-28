package com.a65apps.architecturecomponents.data.sample;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.data.preferences.StringPreferencesSource;

import javax.inject.Inject;

class SampleDataPreference extends StringPreferencesSource {

    @Inject
    SampleDataPreference(@NonNull SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @NonNull
    @Override
    public String key() {
        return PreferenceKeys.SAMPLE_DATA_KEY;
    }
}
