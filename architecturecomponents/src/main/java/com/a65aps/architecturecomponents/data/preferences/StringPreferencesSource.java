package com.a65aps.architecturecomponents.data.preferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@SuppressLint("ApplySharedPref")
public abstract class StringPreferencesSource extends BasePreferencesSource<String> {

    @NonNull
    private final SharedPreferences sharedPreferences;

    public StringPreferencesSource(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    @Override
    protected void performPutData(@NonNull String data) {
        sharedPreferences.edit().putString(key(), data).commit();
    }

    @NonNull
    @Override
    protected String performGetData() {
        return sharedPreferences.getString(key(), "");
    }
}
