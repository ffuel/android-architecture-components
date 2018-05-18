package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BaseCompositeStateModelTest {

    @Mock
    private State state;
    @Mock
    private State compositeState;
    @Mock
    private Router router;

    @Test
    public void testDefaultState() {
        BaseCompositeStateModel<State, State, Router> model =
                new MockCompositeStateModel(state, compositeState, router);
        TestObserver<State> subscriber = new TestObserver<>();

        model.observeDependentState().subscribe(subscriber);
        subscriber.assertValue(this.compositeState);
    }

    @Test
    public void testSetState() {
        BaseCompositeStateModel<State, State, Router> model =
                new MockCompositeStateModel(state, compositeState, router);
        final State newState = new State() {};
        TestObserver<State> subscriber = new TestObserver<>();

        model.setDependentState(newState);
        model.observeDependentState().subscribe(subscriber);
        subscriber.assertValue(newState);
    }

    @Test
    public void testGetState() {
        BaseCompositeStateModel<State, State, Router> model =
                new MockCompositeStateModel(state, compositeState, router);
        final State newState = new State() {};

        assertThat(model.getDependentState(), equalTo(compositeState));
        model.setDependentState(newState);
        assertThat(model.getDependentState(), equalTo(newState));
    }

    @Test
    public void testRestoreState() {
        BaseCompositeStateModel<State, State, Router> model =
                new MockCompositeStateModel(state, compositeState, router);
        model = spy(model);

        model.restoreState(state);

        verify(model, times(1))
                .restoreDependentState(eq(state));
    }

    private static class MockCompositeStateModel extends  BaseCompositeStateModel<State, State, Router> {

        MockCompositeStateModel(@NonNull State defaultState, @NonNull State defaultDependentState,
                                       @NonNull Router router) {
            super(defaultState, defaultDependentState, router);
        }

        @Override
        protected void restoreDependentState(@NonNull State state) {

        }
    }
}
