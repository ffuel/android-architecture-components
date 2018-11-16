package com.a65apps.architecturecomponents.domain.model;

import com.a65apps.architecturecomponents.domain.State;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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
    public void testSubscribeWhileSubscribeAndDispose() {
        Observable<State> observable = Observable.create(StateEmitter.create(state));
        TestObserver<State> observer = new TestObserver<>();

        observable.flatMap(it -> observable)
                .subscribe(observer);
        observer.dispose();

        observer.assertValue(state);
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

    @Test
    public void testUpdateStateWhileSubscribe() {
        State state1 = mock(State.class);
        StateEmitter<State> emitter = StateEmitter.create(state);
        Observable<State> observable = Observable.create(emitter);
        TestObserver<State> observer = new TestObserver<>();

        boolean[] flag = new boolean[1];
        flag[0] = false;
        observable.doOnNext(it -> {
            if (!flag[0]) {
                flag[0] = true;
                emitter.setState(state1);
            }
        }).subscribe(observer);
        observer.dispose();

        observer.assertValues(state, state1);
    }

    @Test
    public void testDisposeWhileDispose() {
        State state1 = mock(State.class);
        StateEmitter<State> emitter = StateEmitter.create(state);
        Observable<State> observable = Observable.create(emitter);
        TestObserver<State> observer1 = new TestObserver<>();
        TestObserver<State> observer2 = new TestObserver<>();

        observable.doOnNext(it -> {
            Observable.create(emitter).subscribe(observer2);
            observer1.dispose();
            observer2.dispose();
            emitter.setState(state1);
        }).subscribe(observer1);

        observer1.assertValue(state);
        observer2.assertNoValues();
    }
}
