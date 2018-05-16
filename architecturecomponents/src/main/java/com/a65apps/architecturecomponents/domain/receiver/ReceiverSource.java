package com.a65apps.architecturecomponents.domain.receiver;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.DataSource;
import com.a65apps.architecturecomponents.domain.State;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ReceiverSource<T extends State> extends DataSource {

    @NonNull
    Observable<T> observeReceiver();

    @NonNull
    Single<T> single();
}
