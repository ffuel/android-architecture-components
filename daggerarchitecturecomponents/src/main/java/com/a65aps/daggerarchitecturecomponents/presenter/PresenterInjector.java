package com.a65aps.daggerarchitecturecomponents.presenter;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65aps.architecturecomponents.domain.Interactor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.Presenter;
import com.a65aps.architecturecomponents.presentation.view.View;

public final class PresenterInjector {

    @NonNull
    public static <S extends State, V extends View<S>, I extends Interactor<S, R>,
    R extends Router, P extends Presenter<S, V, I, R>>
    P build(@NonNull Class<? extends Presenter<S, V, I, R>> presenterClass,
            @NonNull Activity activity) {
        Application application = activity.getApplication();
        if (!(application instanceof HasPresenterSubComponentBuilders)) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasPresenterSubComponentBuilders.class.getCanonicalName()));
        }

        HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders =
                (HasPresenterSubComponentBuilders) application;

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
            @NonNull Fragment fragment) {
        Activity activity = fragment.requireActivity();
        if (!(activity instanceof HasPresenterSubComponentBuilders)) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            activity.getClass().getCanonicalName(),
                            HasPresenterSubComponentBuilders.class.getCanonicalName()));
        }

        HasPresenterSubComponentBuilders hasPresenterSubComponentBuilders =
                (HasPresenterSubComponentBuilders) activity;

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

    private PresenterInjector() {
    }
}
