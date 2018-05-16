package com.a65apps.architecturecomponents.sample.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.source.SingleSource;

import javax.inject.Inject;

import io.reactivex.Single;

class SampleFakeNetworkSource implements SingleSource<String> {

    private static final long TIME_WAIT = 3_000L;

    @NonNull
    private final StringResources resources;

    @Inject
    SampleFakeNetworkSource(@NonNull StringResources resources) {
        this.resources = resources;
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
}
