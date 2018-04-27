package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.source.SingleSource;

public interface SampleSource extends SingleSource<String> {

    @NonNull
    String text();

    @NonNull
    String noConnectionText();
}
