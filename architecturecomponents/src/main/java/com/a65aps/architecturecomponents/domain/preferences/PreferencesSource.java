package com.a65aps.architecturecomponents.domain.preferences;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.source.ObservableSource;
import com.a65aps.architecturecomponents.domain.source.SinglePutSource;

public interface PreferencesSource<T> extends SinglePutSource<T>, ObservableSource<T> {

    @NonNull
    String key();
}
