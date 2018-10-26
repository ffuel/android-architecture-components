package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviConstants;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviState;
import com.a65apps.moxyarchitecturecomponents.presenter.MviMoxyPresenter;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;
import com.arellomobile.mvp.InjectViewState;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class MviPresenter extends MviMoxyPresenter<MviState, MoxyView<MviState>, Router> {

    private static final List<IntentInteractor.Command> INIT_COMMANDS = Arrays.asList(
            IntentInteractor.Command.create(MviConstants.TITLE_INTENT),
            IntentInteractor.Command.create(MviConstants.SUBTITLE_INTENT)
    );

    @Inject
    public MviPresenter(@NonNull ExecutorsFactory executors, @NonNull IntentInteractor<MviState, Router> interactor,
                        @NonNull ApplicationLogger logger) {
        super(executors, interactor, logger);
    }

    @CallSuper
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getInteractor().execute(INIT_COMMANDS);
    }
}
