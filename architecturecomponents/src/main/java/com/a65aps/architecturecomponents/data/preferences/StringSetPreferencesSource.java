package com.a65aps.architecturecomponents.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Set;

@SuppressLint("ApplySharedPref")
public abstract class StringSetPreferencesSource extends BasePreferencesSource<Set<String>> {

    @NonNull
    private final SharedPreferences sharedPreferences;

    public StringSetPreferencesSource(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void performPutData(@NonNull Set<String> data) {
        sharedPreferences.edit().putStringSet(key(), data).commit();
    }

    @NonNull
    @Override
    protected Set<String> performGetData() {
        return sharedPreferences.getStringSet(key(), Collections.emptySet());
    }
}
