package com.a65aps.architecturecomponents.domain.model;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;

import net.jcip.annotations.NotThreadSafe;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@NotThreadSafe
public abstract class BaseModel<S extends State, R extends Router> implements Interactor<S, R> {

    @NonNull
    private final StateEmitter<S> state;
    @NonNull
    private final R router;
    @NonNull
    private final CompositeDisposable disposable = new CompositeDisposable();

    public BaseModel(@NonNull S defaultState, @NonNull R router) {
        this.state = StateEmitter.create(defaultState);
        this.router = router;
    }

    @Override
    public void firstStart() {
//      default implementation
    }

    @NonNull
    @Override
    @CallSuper
    public Observable<S> observeState() {
        return Observable.defer(() -> Observable.create(state));
    }

    @Override
    @CallSuper
    public void restoreState(@NonNull S state) {
        setState(state);
    }

    @Override
    @NonNull
    public R getRouter() {
        return router;
    }

    @Override
    public void dispose() {
        disposable.clear();
    }

    @NonNull
    @CallSuper
    protected S getState() {
        return state.getState();
    }

    @CallSuper
    protected void setState(@NonNull S state) {
        this.state.setState(state);
    }

    protected void addDisposable(@NonNull Disposable disposable) {
        this.disposable.add(disposable);
    }
}
