package com.a65apps.architecturecomponents.sample.domain.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.schedulers.ExecutorsFactory;
import com.a65apps.architecturecomponents.domain.schedulers.ThreadExecutor;
import com.a65apps.architecturecomponents.presentation.navigationv2.Router;
import com.a65apps.architecturecomponents.sample.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TitleIntentTest {

    @Mock
    private StringResources stringResources;
    @Mock
    private ExecutorsFactory executorsFactory;
    @Mock
    private ThreadExecutor executor;
    @Mock
    private IntentInteractor<MviState, Router> interactor;
    @Mock
    private Router router;

    @Before
    public void setup() {
        when(executorsFactory.getExecutor(any())).thenReturn(executor);
        when(executor.getScheduler()).thenReturn(Schedulers.trampoline());
    }

    @Test
    public void executeDefaultChangeTest() {
        when(interactor.observeState()).thenReturn(Observable.just(MviState.DEFAULT));
        when(stringResources.getString(R.string.mvi_title)).thenReturn("test");
        TitleIntent intent = createIntent();
        TestObserver<MviState> observer = new TestObserver<>();

        intent.execute(interactor, router, null).subscribe(observer);

        observer.assertValue(MviState.DEFAULT.mutateTitle("test"));
    }

    @Test
    public void executeAtomicChangeTest() {
        when(interactor.observeState()).thenReturn(Observable.just(MviState.builder()
                .title("")
                .subtitle("subtitle")
                .build()));
        when(stringResources.getString(R.string.mvi_title)).thenReturn("test");
        TitleIntent intent = createIntent();
        TestObserver<MviState> observer = new TestObserver<>();

        intent.execute(interactor, router, null).subscribe(observer);

        observer.assertValue(MviState.builder()
                .title("test")
                .subtitle("subtitle")
                .build());
    }

    @Test
    public void executeErrorTest() {
        Throwable error = new Throwable("error");
        when(interactor.observeState()).thenReturn(Observable.just(MviState.DEFAULT));
        TitleIntent intent = createIntent();
        TestObserver<MviState> observer = new TestObserver<>();

        intent.onError(error, interactor, router).subscribe(observer);

        observer.assertValue(MviState.DEFAULT.mutateTitle("error"));
    }

    @NonNull
    private TitleIntent createIntent() {
        return new TitleIntent(stringResources, executorsFactory);
    }
}
