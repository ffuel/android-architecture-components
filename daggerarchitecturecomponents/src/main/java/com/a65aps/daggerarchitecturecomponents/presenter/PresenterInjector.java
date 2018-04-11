package com.a65aps.daggerarchitecturecomponents.presenter;

import android.app.Application;
import android.support.annotation.NonNull;

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
            @NonNull Application application) {
        if (!(application instanceof HasPresenterSubComponentBuilders)) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasPresenterSubComponentBuilders.class.getCanonicalName()));
        }

        @SuppressWarnings("unchecked")
        ProviderPresenterComponent<S, V, I, R, P> component =
                (ProviderPresenterComponent<S, V, I, R, P>)
                ((HasPresenterSubComponentBuilders) application)
                .getPresenterSubComponentBuilder(presenterClass)
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
