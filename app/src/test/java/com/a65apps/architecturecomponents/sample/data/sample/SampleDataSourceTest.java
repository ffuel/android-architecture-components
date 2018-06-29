package com.a65apps.architecturecomponents.sample.data.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.resources.StringResources;
import com.a65apps.architecturecomponents.domain.source.SinglePutSource;
import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.architecturecomponents.sample.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleDataSourceTest {

    @Mock
    private StringResources resources;
    @Mock
    private SingleSource<String> networkSource;
    @Mock
    private SinglePutSource<String> dbSource;

    @Test
    public void cachedDataTest() {
        when(dbSource.data()).thenReturn(Single.just("test"));
        SampleDataSource source = createSource();
        TestObserver<String> observer = new TestObserver<>();

        source.cachedData().subscribe(observer);

        observer.assertValue("test");
    }

    @Test
    public void textTest() {
        when(resources.getString(eq(R.string.hello_text)))
                .thenReturn("test");
        SampleDataSource source = createSource();

        String result = source.text();

        assertThat(result, equalTo("test"));
    }

    @Test
    public void dataTest() {
        when(networkSource.data()).thenReturn(Single.just("test"));
        when(dbSource.putData(eq("test")))
                .thenReturn(Completable.complete());
        when(dbSource.data()).thenReturn(Single.just("test"));
        SampleDataSource source = createSource();
        TestObserver<String> observer = new TestObserver<>();

        source.data().subscribe(observer);

        observer.assertValue("test");
        verify(networkSource, times(1)).data();
        verify(dbSource, times(1)).data();
        verify(dbSource, times(1)).putData(eq("test"));
    }

    @Test
    public void noConnectionTextTest() {
        when(resources.getString(eq(R.string.no_connection_text)))
                .thenReturn("test");
        SampleDataSource source = createSource();

        String result = source.noConnectionText();

        assertThat(result, equalTo("test"));
    }

    @NonNull
    private SampleDataSource createSource() {
        return new SampleDataSource(resources, networkSource, dbSource);
    }
}
