package com.a65apps.architecturecomponents.sample;

import android.support.v4.content.ContextCompat;

import com.tngtech.jgiven.impl.ByteBuddyStageClassCreator;

import net.bytebuddy.android.AndroidClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import androidx.test.core.app.ApplicationProvider;

public class AndroidStageClassCreator extends ByteBuddyStageClassCreator {

    @Override
    protected ClassLoader getClassLoader(Class<?> stageClass) {
        return ApplicationProvider.getApplicationContext().getClassLoader();
    }

    @Override
    protected ClassLoadingStrategy getClassLoadingStrategy(Class<?> stageClass) {
        return new AndroidClassLoadingStrategy.Wrapping(ContextCompat.getCodeCacheDir(
                ApplicationProvider.getApplicationContext()));
    }
}
