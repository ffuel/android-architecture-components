package com.a65apps.daggerarchitecturecomponents.presenter;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.argument.ArgumentContainer;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

public final class PresenterInjector {

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
            R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders,
            @Nullable Object argument) {
        @SuppressWarnings("unchecked")
        ProviderPresenterComponent<S, V, I, R, P> component =
                (ProviderPresenterComponent<S, V, I, R, P>)
                        hasPresenterSubComponentBuilders.getPresenterSubComponentBuilder(presenterClass)
                                .argument(new ArgumentContainer(argument))
                                .build();

        PresenterProvider<P> presenterProvider = new PresenterProvider<>();
        component.inject(presenterProvider);
        P presenter = presenterProvider.getPresenter();
        presenter.setTag(component);
        return presenter;
    }

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
            R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull Application application,
            @Nullable Object argument) {
        if (!(application instanceof HasPresenterSubComponentBuilders)) {
            throw new IllegalStateException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasPresenterSubComponentBuilders.class.getCanonicalName()));
        }

        HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders =
                (HasPresenterSubComponentBuilders) application;
        return build(presenterClass, hasPresenterSubComponentBuilders, argument);
    }

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
    R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull Application application) {
        return build(presenterClass, application, null);
    }

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
            R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull Fragment fragment,
            @Nullable Object argument) {
        Activity activity = fragment.requireActivity();
        if (!(activity instanceof HasPresenterSubComponentBuilders)) {
            throw new IllegalStateException(
                    String.format(
                            "%s does not implement %s",
                            activity.getClass().getCanonicalName(),
                            HasPresenterSubComponentBuilders.class.getCanonicalName()));
        }

        HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders =
                (HasPresenterSubComponentBuilders) activity;
        return build(presenterClass, hasPresenterSubComponentBuilders, argument);
    }

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
            R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull Fragment fragment) {
        return build(presenterClass, fragment, null);
    }

    private PresenterInjector() {
    }
}
