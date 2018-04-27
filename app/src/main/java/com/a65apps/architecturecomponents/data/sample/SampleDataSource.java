package com.a65apps.architecturecomponents.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.sample.SampleSource;
import com.a65aps.architecturecomponents.domain.resources.StringResources;

import javax.inject.Inject;

import io.reactivex.Single;

public class SampleDataSource implements SampleSource {

    @NonNull
    private final StringResources resources;

    private static final long TIME_WAIT = 3_000L;

    @Inject
    SampleDataSource(@NonNull StringResources resources) {
        this.resources = resources;
    }

    @NonNull
    @Override
    public String text() {
        return resources.getString(R.string.hello_text);
    }

    @NonNull
    @Override
    public Single<String> data() {
        return Single.fromCallable(() -> {
            try {
                Thread.sleep(TIME_WAIT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return resources.getString(R.string.app_name);
        });
    }

    @NonNull
    @Override
    public String noConnectionText() {
        return resources.getString(R.string.no_connection_text);
    }
}
