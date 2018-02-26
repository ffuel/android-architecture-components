package com.a65apps.architecturecomponents.domain.sample;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

public interface SampleSource extends DataSource {

    @NonNull
    String text();
}
