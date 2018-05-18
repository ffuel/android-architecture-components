package com.a65apps.architecturecomponents.data.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RxBroadcastReceiverTest {

    @Mock
    private Context context;
    @Mock
    private IntentFilter intentFilter;

    @Test
    public void testSubscribe() {
        TestObserver<Intent> observer = new TestObserver<>();
        RxBroadcastReceiver.create(context, intentFilter)
                .subscribe(observer);

        observer.dispose();
        verify(context, times(1))
                .registerReceiver(any(), eq(intentFilter));
        verify(context, times(1))
                .unregisterReceiver(any());
    }
}
