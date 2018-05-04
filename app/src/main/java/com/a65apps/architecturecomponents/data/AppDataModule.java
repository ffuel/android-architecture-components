package com.a65apps.architecturecomponents.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.data.sample.SampleDataModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        SampleDataModule.class
})
public class AppDataModule {

    private static final String SHARED_PREFERENCES = "sample_preferences";

    @Provides
    SharedPreferences providesSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }
}
