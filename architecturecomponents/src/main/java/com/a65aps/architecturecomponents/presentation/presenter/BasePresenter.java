package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.view.View;

import net.jcip.annotations.NotThreadSafe;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@NotThreadSafe
public abstract class BasePresenter<S extends State, V extends View<S>, I extends Interactor<S, R>,
        R extends Router>
        implements Presenter<S, V, I, R> {

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

    public BasePresenter(@NonNull ExecutorsFactory executors, @NonNull I interactor,
                         @NonNull ApplicationLogger logger) {
        ioExecutor = executors.getExecutor(SchedulerType.IO);
        computationExecutor = executors.getExecutor(SchedulerType.COMPUTATION);
        uiExecutor = executors.getExecutor(SchedulerType.UI);
        this.interactor = interactor;
        this.logger = logger;
    }

    @CallSuper
    @UiThread
    public void onCreate() {
        interactor.firstStart();
        disposeOnDestroy(interactor.observeState()
                .observeOn(uiExecutor.getScheduler())
                .subscribe(this::onUpdateState, this::onError));
    }

    @CallSuper
    @UiThread
    public void onDestroy() {
        compositeDisposable.dispose();
        interactor.dispose();
    }

    @Override
    @CallSuper
    @UiThread
    public void restoreState(@NonNull S state) {
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
