package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import net.jcip.annotations.NotThreadSafe;

import io.reactivex.Observable;

@NotThreadSafe
public abstract class BaseCompositeStateModel<S extends State, CS extends State, R extends Router>
        extends BaseModel<S, R> implements CompositeStateInteractor<S, CS, R> {

    @NonNull
    private final StateEmitter<CS> dependentState;

    public BaseCompositeStateModel(@NonNull S defaultState, @NonNull CS defaultDependentState,
                                   @NonNull R router) {
        super(defaultState, router);
        this.dependentState = StateEmitter.create(defaultDependentState);
    }

    @NonNull
    @Override
    @CallSuper
    public Observable<CS> observeDependentState() {
        return Observable.defer(() -> Observable.create(dependentState));
    }

    @UiThread
    @Override
    @CallSuper
    public void restoreState(@NonNull S state) {
        super.restoreState(state);
        restoreDependentState(state);
    }

    @NonNull
    @CallSuper
    protected CS getDependentState() {
        return dependentState.getState();
    }

    @CallSuper
    protected void setDependentState(@NonNull CS state) {
        this.dependentState.setState(state);
    }

    @UiThread
    protected abstract void restoreDependentState(@NonNull S state);
}
