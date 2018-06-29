package com.a65apps.architecturecomponents.data.receiver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.a65apps.architecturecomponents.domain.receiver.ConnectionState;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionBroadcastReceiverSourceTest {

    @Mock
    private Context context;
    @Mock
    private ConnectivityManager manager;
    @Mock
    private NetworkInfo networkInfo;

    @Test
    public void observeReceiverTest() {
        ConnectionBroadcastReceiverSource source = new ConnectionBroadcastReceiverSource(context);
        TestObserver<ConnectionState> observer = new TestObserver<>();

        source.observeReceiver().subscribe(observer);

        verify(context, times(1))
                .registerReceiver(any(), any());
    }

    @Test
    public void singleTest() {
        when(context.getSystemService(eq(Context.CONNECTIVITY_SERVICE)))
                .thenReturn(manager);
        when(manager.getActiveNetworkInfo())
                .thenReturn(networkInfo);
        ConnectionBroadcastReceiverSource source = new ConnectionBroadcastReceiverSource(context);
        TestObserver<ConnectionState> observer = new TestObserver<>();

        source.single().subscribe(observer);

        verify(context, times(1))
                .getSystemService(eq(Context.CONNECTIVITY_SERVICE));
        verify(manager, times(1))
                .getActiveNetworkInfo();
        verify(networkInfo, times(1))
                .getTypeName();
        verify(networkInfo, times(1))
                .getSubtypeName();
        verify(networkInfo, times(1))
                .getReason();
        verify(networkInfo, times(1))
                .getExtraInfo();
    }
}
