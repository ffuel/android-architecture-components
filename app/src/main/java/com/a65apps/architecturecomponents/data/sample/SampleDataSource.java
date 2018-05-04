package com.a65apps.architecturecomponents.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.sample.SampleSource;
import com.a65aps.architecturecomponents.domain.resources.StringResources;
import com.a65aps.architecturecomponents.domain.source.SinglePutSource;
import com.a65aps.architecturecomponents.domain.source.SingleSource;
import com.a65aps.daggerarchitecturecomponents.source.Local;
import com.a65aps.daggerarchitecturecomponents.source.Remote;

import javax.inject.Inject;

import io.reactivex.Single;

class SampleDataSource implements SampleSource {

    @NonNull
    private final StringResources resources;
    @NonNull
    private final SingleSource<String> networkSource;
    @NonNull
    private final SinglePutSource<String> dbSource;

    @Inject
    SampleDataSource(@NonNull StringResources resources,
                     @Remote @NonNull SingleSource<String> networkSource,
                     @Local @NonNull SinglePutSource<String> dbSource) {
        this.resources = resources;
        this.networkSource = networkSource;
        this.dbSource = dbSource;
    }

    @NonNull
    @Override
    public Single<String> cachedData() {
        return dbSource.data();
    }

    @NonNull
    @Override
    public String text() {
        return resources.getString(R.string.hello_text);
    }

    @NonNull
    @Override
    public Single<String> data() {
        return networkSource.data()
                .flatMapCompletable(dbSource::putData)
                .andThen(dbSource.data());
    }

    @NonNull
    @Override
    public String noConnectionText() {
        return resources.getString(R.string.no_connection_text);
    }
}
