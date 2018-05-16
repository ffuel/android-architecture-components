package com.a65apps.architecturecomponents.domain.source;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.DataSource;

import io.reactivex.Single;

public interface SingleSource<T> extends DataSource {

    @NonNull
    Single<T> data();
}
