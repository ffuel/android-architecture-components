package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

import io.reactivex.Single;

public interface SampleSource extends DataSource {

    @NonNull
    String text();

    @NonNull
    Single<String> data();

    @NonNull
    String noConnectionText();
}
