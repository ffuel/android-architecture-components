package com.a65apps.architecturecomponents.data.preferences;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.preferences.PreferencesSource;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BasePreferencesSource<T> implements PreferencesSource<T> {

    @NonNull
    private final Subject<T> state = BehaviorSubject.<T>create().toSerialized();

    @NonNull
    @Override
    public Observable<T> observeData() {
        return state;
    }

    @NonNull
    @Override
    public Completable putData(@NonNull T data) {
        return Completable.fromAction(() -> {
            performPutData(data);
            state.onNext(data);
        });
    }

    @NonNull
    @Override
    public Single<T> data() {
        return Single.fromCallable(this::performGetData);
    }

    protected abstract void performPutData(@NonNull T data);

    @NonNull
    protected abstract T performGetData();
}
