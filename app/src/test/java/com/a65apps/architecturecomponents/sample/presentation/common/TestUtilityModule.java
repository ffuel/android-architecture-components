package com.a65apps.architecturecomponents.sample.presentation.common;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Binds;
import dagger.Module;

@Module
public interface TestUtilityModule {

    @Singleton
    @Binds
    @NonNull
    ScreenshotCapture bindsScreenshotCapture(@NonNull StubScreenshotCapture screenshotCapture);
}
