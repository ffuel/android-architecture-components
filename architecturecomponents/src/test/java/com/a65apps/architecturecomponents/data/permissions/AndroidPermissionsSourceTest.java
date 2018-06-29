package com.a65apps.architecturecomponents.data.permissions;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AndroidPermissionsSourceTest {

    private static final String PERMISSION = "com.test.permission";

    @Mock
    private PermissionsManager manager;

    @Test
    public void requestPermissionsTest() {
        AndroidPermissionsSource source = new AndroidPermissionsSource(manager);
        TestObserver<List<PermissionState>> observer = new TestObserver<>();

        source.requestPermissions(false, PERMISSION)
                .subscribe(observer);

        verify(manager, times(1))
                .executeRequest(any());
    }

    @Test
    public void requestPermissionTest() {
        AndroidPermissionsSource source = new AndroidPermissionsSource(manager);
        TestObserver<PermissionState> observer = new TestObserver<>();

        source.requestPermission(false, PERMISSION)
                .subscribe(observer);

        verify(manager, times(1))
                .executeRequest(any());
    }
}
