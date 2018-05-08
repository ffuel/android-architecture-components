package com.a65aps.architecturecomponents.domain.source;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

import io.reactivex.Single;

public interface SingleSourceWithParam<T, Param> extends DataSource {

    @NonNull
    Single<T> data(@NonNull Param param);
}
