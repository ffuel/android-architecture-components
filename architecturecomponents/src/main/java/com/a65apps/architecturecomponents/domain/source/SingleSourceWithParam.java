package com.a65apps.architecturecomponents.domain.source;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.DataSource;

import io.reactivex.Single;

public interface SingleSourceWithParam<T, Param> extends DataSource {

    @NonNull
    Single<T> data(@NonNull Param param);
}
