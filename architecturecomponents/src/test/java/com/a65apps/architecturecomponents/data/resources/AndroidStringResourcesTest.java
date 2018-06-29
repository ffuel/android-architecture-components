package com.a65apps.architecturecomponents.data.resources;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AndroidStringResourcesTest {

    private static final int ID = 1;
    private static final String RESULT = "test";

    @Mock
    private Context context;
    @Mock
    private Resources resources;

    @Before
    public void setup() {
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(eq(ID))).thenReturn(RESULT);
        when(resources.getString(eq(ID), eq("test"))).thenReturn(RESULT);
    }

    @Test
    public void getStringTest() {
        AndroidStringResources stringResources = new AndroidStringResources(context);

        String result = stringResources.getString(ID);

        assertNotNull(result);
        assertThat(result, equalTo(RESULT));
        verify(resources, times(1))
                .getString(eq(ID));
    }

    @Test
    public void getStringWithParamsTest() {
        AndroidStringResources stringResources = new AndroidStringResources(context);

        String result = stringResources.getString(ID, "test");

        assertNotNull(result);
        assertThat(result, equalTo(RESULT));
        verify(resources, times(1))
                .getString(eq(ID), eq("test"));
    }
}
