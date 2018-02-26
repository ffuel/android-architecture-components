package com.a65aps.architecturecomponents.presentation.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65aps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.presentation.navigation.BaseRouter;
import com.a65aps.architecturecomponents.presentation.view.BaseView;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<S extends State, V extends BaseView<S>, I extends Interactor<S>,
        R extends BaseRouter>
        extends MvpPresenter<V> {

    @NonNull
    private final ThreadExecutor ioExecutor;
    @NonNull
    private final ThreadExecutor computationExecutor;
    @NonNull
    private final ThreadExecutor uiExecutor;
    @NonNull
    private final R router;
    @NonNull
    private final I interactor;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private final ApplicationLogger logger;
    @Nullable
    private ProviderPresenterComponent<S, V, I, R, ?> component;

    public BasePresenter(@NonNull ExecutorsFactory executors, @NonNull R router,
                         @NonNull I interactor, @NonNull ApplicationLogger logger) {
        ioExecutor = executors.getExecutor(SchedulerType.IO);
        computationExecutor = executors.getExecutor(SchedulerType.COMPUTATION);
        uiExecutor = executors.getExecutor(SchedulerType.UI);
        this.router = router;
        this.interactor = interactor;
        this.logger = logger;
    }

    @Override
    @CallSuper
    protected void onFirstViewAttach() {
        disposeOnDestroy(interactor.observeState()
                .observeOn(uiExecutor.getScheduler())
                .subscribe(this::onUpdateState, this::onError));
    }

    @CallSuper
    protected void onUpdateState(@NonNull S state) {
        getViewState().updateState(state);
    }

    @CallSuper
    protected void onError(@NonNull Throwable error) {
        getLogger().logError(error);
    }

    protected void disposeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void restoreState(@NonNull S state) {
        interactor.restoreState(state);
    }

    @NonNull
    public ProviderPresenterComponent<S, V, I, R, ?> getComponent() {
        if (component == null) {
            throw new IllegalStateException("presenter is not injected");
        }

        return component;
    }

    public void setComponent(@NonNull ProviderPresenterComponent<S, V, I, R, ?> component) {
        this.component = component;
    }

    public void onBackPressed() {
        getRouter().exit();
    }

    @NonNull
    protected Scheduler getIoScheduler() {
        return ioExecutor.getScheduler();
    }

    @NonNull
    protected Scheduler getComputationScheduler() {
        return computationExecutor.getScheduler();
    }

    @NonNull
    protected Scheduler getUiScheduler() {
        return uiExecutor.getScheduler();
    }

    @NonNull
    protected R getRouter() {
        return router;
    }

    @NonNull
    protected I getInteractor() {
        return interactor;
    }

    @NonNull
    protected ApplicationLogger getLogger() {
        return logger;
    }

    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
