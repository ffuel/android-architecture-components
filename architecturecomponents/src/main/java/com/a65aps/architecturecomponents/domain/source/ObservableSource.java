package com.a65aps.architecturecomponents.domain.source;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.DataSource;

import io.reactivex.Observable;

public interface ObservableSource<T> extends DataSource {

    @NonNull
    Observable<T> observeData();
}
