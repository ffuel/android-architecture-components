package com.a65aps.architecturecomponents.presentation;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.schedulers.SchedulerKey;
import com.a65aps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65aps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65aps.architecturecomponents.presentation.component.UiComponentBuilderFactory;
import com.a65aps.architecturecomponents.presentation.presenter.BasePresenter;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterComponentBuilder;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterSubComponentBuilderFactory;
import com.a65aps.architecturecomponents.presentation.schedulers.UiExecutorThread;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.multibindings.IntoMap;

@Module(includes = {
        AndroidInjectionModule.class
})
public interface PresentationModule {
    @Binds
    @Singleton
    @NonNull
    Context providesContext(Application application);

    @Singleton
    @Binds
    @IntoMap
    @SchedulerKey(SchedulerType.UI)
    @NonNull
    ThreadExecutor bindUiThreadExecutor(UiExecutorThread executor);

    @Singleton
    @Binds
    @NonNull
    UiComponentBuilderFactory<Class<? extends BasePresenter>, PresenterComponentBuilder>
    bindPresenterSubComponentFactory(@NonNull PresenterSubComponentBuilderFactory factory);
}
