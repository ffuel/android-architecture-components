package com.a65aps.architecturecomponents.domain.source;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

import io.reactivex.Completable;

public interface PutSource<T> extends DataSource {

    @NonNull
    Completable putData(@NonNull T data);
}
