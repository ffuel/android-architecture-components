package com.a65apps.architecturecomponents.data.permissions;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsRequestBuffer;
import com.a65apps.architecturecomponents.domain.permissions.RequestPermissionsWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RxPermissionRequestTest {

    @Mock
    private RequestPermissionsWorker worker;

    private PermissionsManager permissionsManager;

    @NonNull
    private final String[] permissions = new String[] {
            "test_permission"
    };
    @NonNull
    private final PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();

    @Before
    public void setup() {
        permissionsManager = new PermissionsManager(buffer);
    }

    @Test
    public void testJustGranted() {
        when(worker.checkPermission(eq("test_permission")))
                .thenReturn(true);
        TestObserver<List<PermissionState>> subscriber = new TestObserver<>();

        RxPermissionRequest
                .create(permissionsManager, permissions, false)
                .subscribe(subscriber);

        buffer.setWorker(worker);
        subscriber.assertValue(Collections.singletonList(PermissionState.GRANTED));
    }

    @Test
    public void testRequestPermissionCalled() {
        when(worker.checkPermission(eq("test_permission")))
                .thenReturn(false);
        when(worker.showRequestPermissionRationale(eq("test_permission")))
                .thenReturn(false);

        Disposable disposable = RxPermissionRequest
                .create(permissionsManager, permissions, false)
                .subscribe();

        buffer.setWorker(worker);

        verify(worker, times(1))
                .setResultCallback(any());
        verify(worker, times(1))
                .requestPermission(eq(permissions), eq(100));

        disposable.dispose();
    }

    @Test
    public void testNeedExplanation() {
        when(worker.checkPermission(eq("test_permission")))
                .thenReturn(false);
        when(worker.showRequestPermissionRationale(eq("test_permission")))
                .thenReturn(true);
        TestObserver<List<PermissionState>> subscriber = new TestObserver<>();

        RxPermissionRequest
                .create(permissionsManager, permissions, false)
                .subscribe(subscriber);

        buffer.setWorker(worker);
        subscriber.assertValue(Collections.singletonList(PermissionState.NEED_EXPLANATION));
    }

    @Test
    public void testNeedExplanationForce() {
        when(worker.checkPermission(eq("test_permission")))
                .thenReturn(false);

        Disposable disposable = RxPermissionRequest
                .create(permissionsManager, permissions, true)
                .subscribe();

        buffer.setWorker(worker);

        verify(worker, times(1))
                .setResultCallback(any());
        verify(worker, times(1))
                .requestPermission(eq(permissions), eq(100));

        disposable.dispose();
    }

    @Test
    public void testRequestPermissionGranted() {
        MockWorker worker = new MockWorker(true, false,
                false);
        TestObserver<List<PermissionState>> subscriber = new TestObserver<>();

        RxPermissionRequest
                .create(permissionsManager, permissions, false)
                .subscribe(subscriber);

        buffer.setWorker(worker);
        subscriber.assertValue(Collections.singletonList(PermissionState.GRANTED));
    }

    @Test
    public void testRequestPermissionShowSettings() {
        MockWorker worker = new MockWorker(false, false,
                false);
        TestObserver<List<PermissionState>> subscriber = new TestObserver<>();

        RxPermissionRequest
                .create(permissionsManager, permissions, false)
                .subscribe(subscriber);

        buffer.setWorker(worker);
        subscriber.assertValue(Collections.singletonList(PermissionState.SHOW_SETTINGS));
    }

    @Test
    public void testRequestPermissionForceNotGranted() {
        MockWorker worker = new MockWorker(false, true,
                false);
        TestObserver<List<PermissionState>> subscriber = new TestObserver<>();

        RxPermissionRequest
                .create(permissionsManager, permissions, true)
                .subscribe(subscriber);

        buffer.setWorker(worker);
        subscriber.assertValue(Collections.singletonList(PermissionState.NOT_GRANTED));
    }

    private static class MockWorker implements RequestPermissionsWorker {

        private final boolean result;
        private final boolean showRationale;
        private final boolean checkPermission;

        @Nullable
        private OnRequestPermissionCallback callback;

        MockWorker(boolean result, boolean showRationale, boolean checkPermission) {
            this.result = result;
            this.showRationale = showRationale;
            this.checkPermission = checkPermission;
        }

        @Override
        public void setResultCallback(@Nullable OnRequestPermissionCallback callback) {
            this.callback = callback;
        }

        @Override
        public void requestPermission(@NonNull String[] permission, int requestCode) {
            if (callback == null) {
                return;
            }

            int[] returnResult = new int[permission.length];
            for (int i = 0; i < returnResult.length; i++) {
                if (result) {
                    returnResult[i] = PackageManager.PERMISSION_GRANTED;
                } else {
                    returnResult[i] = PackageManager.PERMISSION_DENIED;
                }
            }
            callback.onRequestPermissionsResult(requestCode, permission, returnResult);
        }

        @Override
        public boolean showRequestPermissionRationale(@NonNull String permission) {
            return showRationale;
        }

        @Override
        public boolean checkPermission(@NonNull String permission) {
            return checkPermission;
        }
    }
}
