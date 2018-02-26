package com.a65aps.architecturecomponents.domain.model;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseModel<T extends State> implements Interactor<T> {

    @NonNull
    private final Subject<T> state = BehaviorSubject.<T>create().toSerialized();

    @NonNull
    @Override
    @CallSuper
    public Observable<T> observeState() {
        return state;
    }

    @Override
    @CallSuper
    public void restoreState(@NonNull T state) {
        setState(state);
    }

    @NonNull
    @CallSuper
    protected T getState() {
        return state.blockingFirst();
    }

    @CallSuper
    protected void setState(@NonNull T state) {
        this.state.onNext(state);
    }
}
