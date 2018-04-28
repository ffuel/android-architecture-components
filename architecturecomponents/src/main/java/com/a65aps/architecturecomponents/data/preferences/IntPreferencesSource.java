package com.a65aps.architecturecomponents.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@SuppressLint("ApplySharedPref")
public abstract class IntPreferencesSource extends BasePreferencesSource<Integer> {

    @NonNull
    private final SharedPreferences sharedPreferences;

    public IntPreferencesSource(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void performPutData(@NonNull Integer data) {
        sharedPreferences.edit().putInt(key(), data).commit();
    }

    @NonNull
    @Override
    protected Integer performGetData() {
        return sharedPreferences.getInt(key(), 0);
    }
}
