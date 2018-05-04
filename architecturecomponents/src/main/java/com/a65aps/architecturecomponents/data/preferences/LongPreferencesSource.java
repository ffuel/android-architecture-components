package com.a65aps.architecturecomponents.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@SuppressLint("ApplySharedPref")
public abstract class LongPreferencesSource extends BasePreferencesSource<Long> {

    @NonNull
    private final SharedPreferences sharedPreferences;

    public LongPreferencesSource(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void performPutData(@NonNull Long data) {
        sharedPreferences.edit().putLong(key(), data).commit();
    }

    @NonNull
    @Override
    protected Long performGetData() {
        return sharedPreferences.getLong(key(), 0L);
    }
}
