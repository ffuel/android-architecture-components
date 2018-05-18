package com.a65apps.architecturecomponents.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@SuppressLint("ApplySharedPref")
public abstract class FloatPreferencesSource extends BasePreferencesSource<Float> {

    @NonNull
    private final SharedPreferences sharedPreferences;

    public FloatPreferencesSource(@NonNull SharedPreferences sharedPreferences) {
        super(0.0F);
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void performPutData(@NonNull Float data) {
        sharedPreferences.edit().putFloat(key(), data).commit();
    }

    @NonNull
    @Override
    protected Float performGetData() {
        return sharedPreferences.getFloat(key(), 0.0f);
    }
}
