package com.a65apps.daggerarchitecturecomponents.presenter;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.a65apps.architecturecomponents.presentation.view.View;

public final class PresenterInjector {

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
            R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders) {
        @SuppressWarnings("unchecked")
        ProviderPresenterComponent<S, V, I, R, P> component =
                (ProviderPresenterComponent<S, V, I, R, P>)
                        hasPresenterSubComponentBuilders.getPresenterSubComponentBuilder(presenterClass)
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
            @NonNull Activity activity) {
        Application application = activity.getApplication();
        if (!(application instanceof HasPresenterSubComponentBuilders)) {
            throw new IllegalStateException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasPresenterSubComponentBuilders.class.getCanonicalName()));
        }

        HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders =
                (HasPresenterSubComponentBuilders) application;
        return build(presenterClass, hasPresenterSubComponentBuilders);
    }

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
            R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull Fragment fragment) {
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
        return build(presenterClass, hasPresenterSubComponentBuilders);
    }

    private PresenterInjector() {
    }
}
