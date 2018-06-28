package com.a65apps.architecturecomponents.domain.schedulers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExecutorsFactoryTest {

    @Mock
    private Map<SchedulerType, ThreadExecutor> executors;
    @Mock
    private ThreadExecutor executor;

    @Test
    public void getExecutorTest() {
        when(executors.get(eq(SchedulerType.IO)))
                .thenReturn(executor);
        ExecutorsFactory factory = new ExecutorsFactory(executors);

        ThreadExecutor result = factory.getExecutor(SchedulerType.IO);
        assertNotNull(result);
        assertThat(result, equalTo(executor));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExecutorNotFoundTest() {
        when(executors.get(eq(SchedulerType.IO)))
                .thenReturn(null);
        ExecutorsFactory factory = new ExecutorsFactory(executors);

        factory.getExecutor(SchedulerType.IO);
    }
}
