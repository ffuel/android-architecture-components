package com.a65apps.moxyarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;

public abstract class MviMoxyPresenter<S extends State, V extends MoxyView<S>, R extends Router>
        extends MoxyPresenter<S, V, IntentInteractor<S, R>, R> {

    public MviMoxyPresenter(@NonNull ExecutorsFactory executors, @NonNull IntentInteractor<S, R> interactor,
                            @NonNull ApplicationLogger logger) {
        super(executors, interactor, logger);
    }
}
