package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.FlowableIntent;
import com.a65apps.architecturecomponents.domain.IntentFactory;
import com.a65apps.architecturecomponents.domain.MaybeIntent;
import com.a65apps.architecturecomponents.domain.ObservableIntent;
import com.a65apps.architecturecomponents.domain.SingleIntent;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.StateProvider;
import com.a65apps.architecturecomponents.domain.log.ApplicationLogger;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IntentModelTest {

    @Mock
    private StateProvider<State> stateProvider;
    @Mock
    private Router router;
    @Mock
    private IntentFactory intentFactory;
    @Mock
    private ApplicationLogger logger;
    @Mock
    private State state;
    @Mock
    private State changedState;
    @Mock
    private State thirdState;
    @Mock
    private State errorState;
    @Mock
    private SingleIntent<State, Router> singleIntent;
    @Mock
    private ObservableIntent<State, Router> observableIntent;
    @Mock
    private FlowableIntent<State, Router> flowableIntent;
    @Mock
    private MaybeIntent<State, Router> maybeIntent;

    @Before
    public void setup() {
        when(stateProvider.provide()).thenReturn(state);
    }

    @Test
    public void constructorTest() {
        IntentModel<State, Router> model = createModel();
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);

        observer.assertValue(state);
    }

    @Test
    public void executeSingleTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("test"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Single.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test");
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(singleIntent).execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void executeSingleWithParamTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        when(intentFactory.get(eq("test"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Single.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(singleIntent).execute(eq(model), eq(router), eq(param));
    }

    @Test
    public void executeSingleErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Single.error(throwable));
        when(singleIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Single.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(singleIntent).execute(eq(model), eq(router), eq(param));
        verify(singleIntent).onError(eq(throwable), eq(model), eq(router));
    }

    @Test
    public void executeSingleFatalErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Single.error(throwable));
        when(singleIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Single.error(throwable));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(state);
        verify(singleIntent).execute(eq(model), eq(router), eq(param));
        verify(singleIntent).onError(eq(throwable), eq(model), eq(router));
        verify(logger).logError(eq(throwable));
    }

    @Test
    public void executeObservableTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("test"))).thenReturn(observableIntent);
        when(observableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Observable.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test");
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(observableIntent).execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void executeObservableWithParamTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        when(intentFactory.get(eq("test"))).thenReturn(observableIntent);
        when(observableIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Observable.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(observableIntent).execute(eq(model), eq(router), eq(param));
    }

    @Test
    public void executeObservableErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(observableIntent);
        when(observableIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Observable.error(throwable));
        when(observableIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Observable.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(observableIntent).execute(eq(model), eq(router), eq(param));
        verify(observableIntent).onError(eq(throwable), eq(model), eq(router));
    }

    @Test
    public void executeObservableFatalErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(observableIntent);
        when(observableIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Observable.error(throwable));
        when(observableIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Observable.error(throwable));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(state);
        verify(observableIntent).execute(eq(model), eq(router), eq(param));
        verify(observableIntent).onError(eq(throwable), eq(model), eq(router));
        verify(logger).logError(eq(throwable));
    }

    @Test
    public void executeFlowableTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("test"))).thenReturn(flowableIntent);
        when(flowableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Flowable.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test");
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(flowableIntent).execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void executeFlowableWithParamTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        when(intentFactory.get(eq("test"))).thenReturn(flowableIntent);
        when(flowableIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Flowable.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(flowableIntent).execute(eq(model), eq(router), eq(param));
    }

    @Test
    public void executeFlowableErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(flowableIntent);
        when(flowableIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Flowable.error(throwable));
        when(flowableIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Flowable.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(flowableIntent).execute(eq(model), eq(router), eq(param));
        verify(flowableIntent).onError(eq(throwable), eq(model), eq(router));
    }

    @Test
    public void executeFlowableFatalErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(flowableIntent);
        when(flowableIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Flowable.error(throwable));
        when(flowableIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Flowable.error(throwable));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(state);
        verify(flowableIntent).execute(eq(model), eq(router), eq(param));
        verify(flowableIntent).onError(eq(throwable), eq(model), eq(router));
        verify(logger).logError(eq(throwable));
    }

    @Test
    public void executeMaybeTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("test"))).thenReturn(maybeIntent);
        when(maybeIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Maybe.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test");
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(maybeIntent).execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void executeMaybeWithParamTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        when(intentFactory.get(eq("test"))).thenReturn(maybeIntent);
        when(maybeIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Maybe.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(maybeIntent).execute(eq(model), eq(router), eq(param));
    }

    @Test
    public void executeMaybeErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(maybeIntent);
        when(maybeIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Maybe.error(throwable));
        when(maybeIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Maybe.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(changedState);
        verify(maybeIntent).execute(eq(model), eq(router), eq(param));
        verify(maybeIntent).onError(eq(throwable), eq(model), eq(router));
    }

    @Test
    public void executeMaybeFatalErrorTest() {
        IntentModel<State, Router> model = createModel();
        Object param = new Object();
        Throwable throwable = new Throwable();
        when(intentFactory.get(eq("test"))).thenReturn(maybeIntent);
        when(maybeIntent.execute(eq(model), eq(router), eq(param)))
                .thenReturn(Maybe.error(throwable));
        when(maybeIntent.onError(eq(throwable), eq(model), eq(router)))
                .thenReturn(Maybe.error(throwable));
        TestObserver<State> observer = new TestObserver<>();

        model.execute("test", param);
        model.observeState().subscribe(observer);

        observer.assertValue(state);
        verify(maybeIntent).execute(eq(model), eq(router), eq(param));
        verify(maybeIntent).onError(eq(throwable), eq(model), eq(router));
        verify(logger).logError(eq(throwable));
    }

    @Test(expected = Throwable.class)
    public void unknownIntentTest() {
        when(intentFactory.get(eq("test"))).thenThrow(Throwable.class);
        IntentModel<State, Router> model = createModel();

        model.execute("type");
    }

    @Test
    public void disposeSingleTest() {
        IntentModel<State, Router> model = createModel();
        PublishSubject<State> subject = PublishSubject.create();
        when(intentFactory.get(eq("test"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject.firstOrError());
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.execute("test");
        model.dispose("test");
        subject.onNext(changedState);
        model.execute("test");
        model.execute("test");
        model.execute("test");
        subject.onNext(changedState);

        observer.assertValues(state, changedState);
        verify(singleIntent, times(4))
                .execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void disposeObservableTest() {
        IntentModel<State, Router> model = createModel();
        PublishSubject<State> subject = PublishSubject.create();
        when(intentFactory.get(eq("test"))).thenReturn(observableIntent);
        when(observableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject);
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.execute("test");
        model.dispose("test");
        subject.onNext(changedState);
        model.execute("test");
        model.execute("test");
        model.execute("test");
        subject.onNext(changedState);
        subject.onNext(thirdState);

        observer.assertValues(state, changedState, thirdState);
        verify(observableIntent, times(4))
                .execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void disposeFlowableTest() {
        IntentModel<State, Router> model = createModel();
        PublishSubject<State> subject = PublishSubject.create();
        when(intentFactory.get(eq("test"))).thenReturn(flowableIntent);
        when(flowableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject.toFlowable(BackpressureStrategy.BUFFER));
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.execute("test");
        model.dispose("test");
        subject.onNext(changedState);
        model.execute("test");
        model.execute("test");
        model.execute("test");
        subject.onNext(changedState);
        subject.onNext(thirdState);

        observer.assertValues(state, changedState, thirdState);
        verify(flowableIntent, times(4))
                .execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void disposeMaybeTest() {
        IntentModel<State, Router> model = createModel();
        PublishSubject<State> subject = PublishSubject.create();
        when(intentFactory.get(eq("test"))).thenReturn(maybeIntent);
        when(maybeIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject.firstElement());
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.execute("test");
        model.dispose("test");
        subject.onNext(changedState);
        model.execute("test");
        model.execute("test");
        model.execute("test");
        subject.onNext(changedState);

        observer.assertValues(state, changedState);
        verify(maybeIntent, times(4))
                .execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void disposeAllTest() {
        IntentModel<State, Router> model = createModel();
        PublishSubject<State> subject = PublishSubject.create();
        when(intentFactory.get(eq("single"))).thenReturn(singleIntent);
        when(intentFactory.get(eq("maybe"))).thenReturn(maybeIntent);
        when(intentFactory.get(eq("observable"))).thenReturn(observableIntent);
        when(intentFactory.get(eq("flowable"))).thenReturn(flowableIntent);
        when(maybeIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject.firstElement());
        when(flowableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject.toFlowable(BackpressureStrategy.BUFFER));
        when(observableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject);
        when(singleIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(subject.firstOrError());
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.execute("single");
        model.execute("maybe");
        model.execute("observable");
        model.execute("flowable");
        model.dispose();
        subject.onNext(changedState);

        observer.assertValue(state);
        verify(singleIntent).execute(eq(model), eq(router), nullable(Object.class));
        verify(maybeIntent).execute(eq(model), eq(router), nullable(Object.class));
        verify(observableIntent).execute(eq(model), eq(router), nullable(Object.class));
        verify(flowableIntent).execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void executeCollectionTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("single"))).thenReturn(singleIntent);
        when(intentFactory.get(eq("maybe"))).thenReturn(maybeIntent);
        when(intentFactory.get(eq("observable"))).thenReturn(observableIntent);
        when(intentFactory.get(eq("flowable"))).thenReturn(flowableIntent);
        when(maybeIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Maybe.just(changedState));
        when(flowableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Flowable.just(changedState));
        when(observableIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Observable.just(changedState));
        when(singleIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Single.just(changedState));
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.execute(Arrays.asList(
                IntentInteractor.Command.create("single"),
                IntentInteractor.Command.create("maybe"),
                IntentInteractor.Command.create("observable"),
                IntentInteractor.Command.create("flowable")));

        observer.assertValues(state, changedState, changedState, changedState, changedState);

        verify(singleIntent).execute(eq(model), eq(router), nullable(Object.class));
        verify(maybeIntent).execute(eq(model), eq(router), nullable(Object.class));
        verify(observableIntent).execute(eq(model), eq(router), nullable(Object.class));
        verify(flowableIntent).execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void chainExecuteTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("one"))).thenReturn(singleIntent);
        when(intentFactory.get(eq("two"))).thenReturn(singleIntent);
        when(intentFactory.get(eq("three"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Single.just(changedState))
                .thenReturn(Single.just(thirdState))
                .thenReturn(Single.just(state));
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.chainExecute(Arrays.asList(
                IntentInteractor.Command.create("one"),
                IntentInteractor.Command.create("two"),
                IntentInteractor.Command.create("three")));

        observer.assertValues(state, changedState, thirdState, state);
        verify(singleIntent, times(3))
                .execute(eq(model), eq(router), nullable(Object.class));
    }

    @Test
    public void unsupportedChainExecutionIntentTest() {
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("test"))).thenReturn(observableIntent);
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.chainExecute(Collections.singletonList(IntentInteractor.Command.create("test")));

        verify(logger).logError(argThat(argument -> argument instanceof IllegalArgumentException
                && argument.getMessage()
                .equals("Unsupported intent type. Chain execution supports only SingleIntent")));
    }

    @Test
    public void chainExecuteErrorTest() {
        Throwable error = new Throwable();
        IntentModel<State, Router> model = createModel();
        when(intentFactory.get(eq("one"))).thenReturn(singleIntent);
        when(intentFactory.get(eq("two"))).thenReturn(singleIntent);
        when(intentFactory.get(eq("three"))).thenReturn(singleIntent);
        when(singleIntent.execute(eq(model), eq(router), nullable(Object.class)))
                .thenReturn(Single.just(changedState))
                .thenReturn(Single.error(error))
                .thenReturn(Single.just(state));
        when(singleIntent.onError(eq(error), eq(model), eq(router)))
                .thenReturn(Single.just(errorState));
        TestObserver<State> observer = new TestObserver<>();

        model.observeState().subscribe(observer);
        model.chainExecute(Arrays.asList(
                IntentInteractor.Command.create("one"),
                IntentInteractor.Command.create("two"),
                IntentInteractor.Command.create("three")));

        observer.assertValues(state, changedState, errorState, state);
        verify(singleIntent, times(3))
                .execute(eq(model), eq(router), nullable(Object.class));
        verify(singleIntent)
                .onError(eq(error), eq(model), eq(router));
    }

    @NonNull
    private IntentModel<State, Router> createModel() {
        return new IntentModel<>(stateProvider, router, intentFactory, logger);
    }
}
