package com.a65apps.architecturecomponents.sample.domain.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.source.SingleSource;

import io.reactivex.Single;

public interface SampleSource extends SingleSource<String> {

    @NonNull
    Single<String> cachedData();

    @NonNull
    String text();

    @NonNull
    String noConnectionText();
}
