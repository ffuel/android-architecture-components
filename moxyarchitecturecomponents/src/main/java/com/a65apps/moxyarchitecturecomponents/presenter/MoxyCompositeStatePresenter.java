package com.a65apps.moxyarchitecturecomponents.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.CompositeStatePresenter;
import com.a65apps.moxyarchitecturecomponents.view.MoxyCompositeStateView;
import com.arellomobile.mvp.MvpPresenter;

import net.jcip.annotations.NotThreadSafe;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@NotThreadSafe
public class MoxyCompositeStatePresenter<S extends State, CS extends State,
        V extends MoxyCompositeStateView<S, CS>, I extends CompositeStateInteractor<S, CS, R>,
        R extends Router> extends MvpPresenter<V> implements CompositeStatePresenter<S, CS, V, I, R> {

    @NonNull
    private final ThreadExecutor ioExecutor;
    @NonNull
    private final ThreadExecutor computationExecutor;
    @NonNull
    private final ThreadExecutor uiExecutor;
    @NonNull
    private final I interactor;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private final ApplicationLogger logger;
    @Nullable
    private Object tag = null;

    private boolean isRestoring = false;

    public MoxyCompositeStatePresenter(@NonNull ExecutorsFactory executors, @NonNull I interactor,
                                       @NonNull ApplicationLogger logger) {
        ioExecutor = executors.getExecutor(SchedulerType.IO);
        computationExecutor = executors.getExecutor(SchedulerType.COMPUTATION);
        uiExecutor = executors.getExecutor(SchedulerType.UI);
        this.interactor = interactor;
        this.logger = logger;
    }

    @Override
    @CallSuper
    @UiThread
    protected void onFirstViewAttach() {
        interactor.firstStart(isRestoring);
        isRestoring = false;
        disposeOnDestroy(interactor.observeState()
                .observeOn(uiExecutor.getScheduler())
                .subscribe(this::onUpdateState, this::onError));
        disposeOnDestroy(interactor.observeDependentState()
                .observeOn(uiExecutor.getScheduler())
                .subscribe(this::onUpdateDependentState, this::onError));
    }

    @Override
    @CallSuper
    @UiThread
    public void onDestroy() {
        compositeDisposable.dispose();
        interactor.dispose();
    }

    @Nullable
    @Override
    public V getView() {
        return getViewState();
    }

    @Override
    @CallSuper
    @UiThread
    public void restoreState(@NonNull S state) {
        isRestoring = true;
        interactor.restoreState(state);
    }

    @Override
    @NonNull
    public final R getRouter() {
        return getInteractor().getRouter();
    }

    @Override
    @NonNull
    public final I getInteractor() {
        return interactor;
    }

    @Override
    @UiThread
    public void onBackPressed() {
        getRouter().exit();
    }

    @Override
    public void setTag(@Nullable Object tag) {
        this.tag = tag;
    }

    @Override
    @Nullable
    public Object getTag() {
        return tag;
    }

    @CallSuper
    @UiThread
    protected void onUpdateState(@NonNull S state) {
        V view = getView();
        if (view != null) {
            view.updateState(state);
        }
    }

    @CallSuper
    @UiThread
    protected void onUpdateDependentState(@NonNull CS state) {
        V view = getView();
        if (view != null) {
            view.updateDependentState(state);
        }
    }

    @CallSuper
    @UiThread
    protected void onError(@NonNull Throwable error) {
        getLogger().logError(error);
    }

    @CallSuper
    @UiThread
    protected final void disposeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @NonNull
    protected final Scheduler getIoScheduler() {
        return ioExecutor.getScheduler();
    }

    @NonNull
    protected final Scheduler getComputationScheduler() {
        return computationExecutor.getScheduler();
    }

    @NonNull
    protected final Scheduler getUiScheduler() {
        return uiExecutor.getScheduler();
    }

    @NonNull
    protected final ApplicationLogger getLogger() {
        return logger;
    }
}
