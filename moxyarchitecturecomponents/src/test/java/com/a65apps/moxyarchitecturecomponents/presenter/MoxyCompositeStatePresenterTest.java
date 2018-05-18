package com.a65apps.moxyarchitecturecomponents.presenter;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.SchedulerType;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;
import com.a65apps.moxyarchitecturecomponents.view.MoxyCompositeStateView;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;
import com.arellomobile.mvp.viewstate.MvpViewState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoxyCompositeStatePresenterTest {
    @Mock
    private CompositeStateInteractor<State, State, Router> interactor;
    @Mock
    private ApplicationLogger logger;
    @Mock
    private ThreadExecutor executor;
    @Mock
    private State state;
    @Mock
    private State dependentState;

    private MoxyCompositeStatePresenter<State, State, MoxyCompositeStateView<State, State>,
            CompositeStateInteractor<State, State, Router>,
            Router> presenter;

    private final MockViewState view = spy(new MockViewState());

    @Before
    public void setup() {
        when(executor.getScheduler())
                .thenReturn(Schedulers.trampoline());
        when(interactor.observeState())
                .thenReturn(Observable.just(state));
        when(interactor.observeDependentState())
                .thenReturn(Observable.just(dependentState));

        presenter = new MoxyCompositeStatePresenter<State, State, MoxyCompositeStateView<State, State>,
                CompositeStateInteractor<State, State, Router>,
                Router>(new ExecutorsFactory(
                new HashMap<SchedulerType, ThreadExecutor>() {
                    {
                        put(SchedulerType.IO, executor);
                        put(SchedulerType.UI, executor);
                        put(SchedulerType.COMPUTATION, executor);
                    }
                }
        ), interactor, logger) {};
    }

    @Test
    public void testOnFirstViewAttach() {
        presenter.setViewState(view);
        presenter.attachView(view);

        verify(interactor, times(1))
                .firstStart(eq(false));
        verify(interactor, times(1))
                .observeState();
        verify(interactor, times(1))
                .observeDependentState();
        verify(view, times(1))
                .updateState(eq(state));
        verify(view, times(1))
                .updateDependentState(eq(dependentState));
    }

    @Test
    public void testOnDestroy() {
        presenter.attachView(view);
        presenter.onDestroy();

        verify(interactor, times(1))
                .dispose();
    }

    @Test
    public void testRestoreState() {
        presenter.attachView(view);
        presenter.restoreState(state);

        verify(interactor, times(1))
                .restoreState(eq(state));
    }

    private static class MockViewState extends MvpViewState<MoxyCompositeStateView<State, State>>
            implements MoxyCompositeStateView<State, State> {

        @Override
        public void updateState(@NonNull State state) {

        }

        @Override
        public void updateDependentState(@NonNull State state) {

        }
    }
}
