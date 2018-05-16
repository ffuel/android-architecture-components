package com.a65apps.architecturecomponents.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@SuppressLint("ApplySharedPref")
public abstract class BooleanPreferencesSource extends BasePreferencesSource<Boolean> {

    @NonNull
    private final SharedPreferences sharedPreferences;

    public BooleanPreferencesSource(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void performPutData(@NonNull Boolean data) {
        sharedPreferences.edit().putBoolean(key(), data).commit();
    }

    @NonNull
    @Override
    protected Boolean performGetData() {
        return sharedPreferences.getBoolean(key(), false);
    }
}
