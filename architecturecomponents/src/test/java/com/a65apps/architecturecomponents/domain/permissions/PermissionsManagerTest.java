package com.a65apps.architecturecomponents.domain.permissions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PermissionsManagerTest {

    @Mock
    private PermissionsRequestBuffer buffer;
    @Mock
    private PermissionsRequest request;

    @Test
    public void executeRequestTest() {
        PermissionsManager manager = new PermissionsManager(buffer);

        manager.executeRequest(request);

        verify(buffer, times(1))
                .executeRequest(eq(request));
    }
}
