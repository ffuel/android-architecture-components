package com.a65apps.architecturecomponents.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.sample.SampleSource;
import com.a65aps.architecturecomponents.domain.resources.StringResources;

import javax.inject.Inject;

public class SampleDataSource implements SampleSource {

    @NonNull
    private final StringResources resources;

    @Inject
    public SampleDataSource(@NonNull StringResources resources) {
        this.resources = resources;
    }

    @NonNull
    @Override
    public String text() {
        return resources.getString(R.string.hello_text);
    }
}
