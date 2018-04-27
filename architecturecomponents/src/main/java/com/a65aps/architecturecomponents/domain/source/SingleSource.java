package com.a65aps.architecturecomponents.domain.source;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

import io.reactivex.Single;

public interface SingleSource<T> extends DataSource {

    @NonNull
    Single<T> data();
}
