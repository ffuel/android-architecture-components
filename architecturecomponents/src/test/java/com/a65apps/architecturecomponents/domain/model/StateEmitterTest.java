package com.a65apps.architecturecomponents.domain.model;

import com.a65apps.architecturecomponents.domain.State;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.ObservableEmitter;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StateEmitterTest {

    @Mock
    private State state;
    @Mock
    private ObservableEmitter<State> mockEmitter1;
    @Mock
    private ObservableEmitter<State> mockEmitter2;

    @Test
    public void testConstructor() {
        StateEmitter<State> emitter = StateEmitter.create(state);

        assertThat(emitter.getState(), equalTo(state));
    }

    @Test
    public void testSubscribe() {
        StateEmitter<State> emitter = StateEmitter.create(state);

        emitter.subscribe(mockEmitter1);

        verify(mockEmitter1, times(1))
                .setDisposable(any());
        verify(mockEmitter1, times(1))
                .onNext(eq(state));
    }

    @Test
    public void testSubscribeMultiple() {
        StateEmitter<State> emitter = StateEmitter.create(state);

        emitter.subscribe(mockEmitter1);
        emitter.subscribe(mockEmitter2);

        verify(mockEmitter1, times(1))
                .setDisposable(any());
        verify(mockEmitter1, times(1))
                .onNext(eq(state));

        verify(mockEmitter2, times(1))
                .setDisposable(any());
        verify(mockEmitter2, times(1))
                .onNext(eq(state));
    }

    @Test
    public void testUpdateState() {
        StateEmitter<State> emitter = StateEmitter.create(state);

        emitter.subscribe(mockEmitter1);
        State newState = new State() {};
        emitter.setState(newState);

        assertThat(emitter.getState(), equalTo(newState));

        verify(mockEmitter1, times(1))
                .setDisposable(any());
        verify(mockEmitter1, times(2))
                .onNext(any());
    }
}
