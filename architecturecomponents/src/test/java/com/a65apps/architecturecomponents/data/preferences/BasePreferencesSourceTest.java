package com.a65apps.architecturecomponents.data.preferences;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class BasePreferencesSourceTest {

    @Test
    public void testObserveDefaultData() {
        Object data = new Object();
        MockSource source = new MockSource(data);
        TestObserver<Object> subscriber = new TestObserver<>();

        source.observeData().subscribe(subscriber);
        subscriber.assertValue(data);
    }

    @Test
    public void testPutData() {
        Object data = new Object();
        MockSource source = new MockSource(new Object());
        TestObserver<Object> subscriber = new TestObserver<>();

        source.putData(data).subscribe();
        source.observeData().subscribe(subscriber);
        subscriber.assertValue(data);
    }

    @Test
    public void testGetDefaultData() {
        Object data = new Object();
        MockSource source = new MockSource(data);
        TestObserver<Object> subscriber = new TestObserver<>();

        source.data().subscribe(subscriber);
        subscriber.assertValue(data);
    }

    @Test
    public void testGetPuttedData() {
        Object data = new Object();
        MockSource source = new MockSource(new Object());
        TestObserver<Object> subscriber = new TestObserver<>();

        source.putData(data).subscribe();
        source.data().subscribe(subscriber);
        subscriber.assertValue(data);
    }

    @Test
    public void testChangeData() {
        Object data = new Object();
        MockSource source = new MockSource(new Object());
        TestObserver<Object> subscriber = new TestObserver<>();

        source.observeData()
                .skip(1)
                .subscribe(subscriber);

        source.putData(data).subscribe();

        subscriber.assertValue(data);
    }

    private static class MockSource extends BasePreferencesSource<Object> {

        @NonNull
        private Object data;

        private MockSource(@NonNull Object data) {
            super(data);
            this.data = data;
        }

        @Override
        protected void performPutData(@NonNull Object data) {
            this.data = data;
        }

        @NonNull
        @Override
        protected Object performGetData() {
            return data;
        }

        @NonNull
        @Override
        public String key() {
            return "key";
        }
    }
}
