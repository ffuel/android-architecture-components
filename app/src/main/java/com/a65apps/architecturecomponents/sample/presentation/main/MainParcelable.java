package com.a65apps.architecturecomponents.sample.presentation.main;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.main.Screen;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MainParcelable implements Parcelable {

    public static MainParcelable create(Screen screen) {
        return new AutoValue_MainParcelable(screen);
    }

    @NonNull
    public abstract Screen screen();
}
