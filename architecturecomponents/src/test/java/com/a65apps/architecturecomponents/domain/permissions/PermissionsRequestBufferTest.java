package com.a65apps.architecturecomponents.domain.permissions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PermissionsRequestBufferTest {

    @Mock
    private RequestPermissionsWorker worker;
    @Mock
    private PermissionsRequest request;

    @Test
    public void testSetWorkerEmptyQueue() {
        PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();
        PermissionsRequestBuffer spyBuffer = spy(buffer);

        spyBuffer.setWorker(worker);

        verify(spyBuffer, times(0))
                .executeRequest(any());
    }

    @Test
    public void testExecuteRequest() {
        PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();

        buffer.setWorker(worker);
        buffer.executeRequest(request);
        buffer.executeRequest(request);

        verify(request, times(2))
                .execute(eq(worker));
    }

    @Test
    public void testExecuteRequestAfterAttachWorker() {
        PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();

        buffer.executeRequest(request);
        buffer.executeRequest(request);
        buffer.setWorker(worker);

        verify(request, times(2))
                .execute(eq(worker));
    }

    @Test
    public void testDetachWorker() {
        PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();

        buffer.setWorker(worker);
        buffer.executeRequest(request);
        buffer.setWorker(null);
        buffer.executeRequest(request);
        verify(request, times(1))
                .execute(eq(worker));
    }

    @Test
    public void testNotWorkerRequest() {
        PermissionsRequestBuffer buffer = new PermissionsRequestBuffer();

        buffer.executeRequest(request);
        buffer.executeRequest(request);

        verify(request, times(0))
                .execute(any());
    }
}
