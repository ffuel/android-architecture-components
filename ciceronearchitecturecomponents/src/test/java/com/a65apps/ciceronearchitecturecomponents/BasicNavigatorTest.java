package com.a65apps.ciceronearchitecturecomponents;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicNavigatorTest {

    @Mock
    private FragmentActivity activity;
    @Mock
    private ContainerIdProvider idProvider;
    @Mock
    private Map<String, FragmentFabric> fragmentMap;
    @Mock
    private Map<String, IntentFabric> intentMap;
    @Mock
    private IntentFabric intentFabric;
    @Mock
    private FragmentFabric fragmentFabric;
    @Mock
    private Context context;
    @Mock
    private Intent intent;
    @Mock
    private Fragment fragment;

    @Test
    public void createActivityIntentTest() {
        when(intentMap.get(eq("test")))
                .thenReturn(intentFabric);
        when(intentFabric.build(eq(context), any(), isNull()))
                .thenReturn(intent);
        BasicNavigator navigator = createNavigator();

        Intent intent = navigator.createActivityIntent(context, "test", null);
        Intent intentNull = navigator.createActivityIntent(context, "test1", null);

        assertThat(intent, equalTo(intent));
        assertTrue(intentNull == null);
        verify(intentFabric, times(1))
                .build(eq(context), any(), isNull());
    }

    @Test
    public void createFragmentTest() {
        when(fragmentMap.get(eq("test")))
                .thenReturn(fragmentFabric);
        when(fragmentFabric.build(any(), isNull()))
                .thenReturn(fragment);
        BasicNavigator navigator = createNavigator();

        Fragment fragment = navigator.createFragment("test", null);

        assertThat(fragment, equalTo(this.fragment));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFragmentNotFoundTest() {
        BasicNavigator navigator = createNavigator();
        navigator.createFragment("test", null);
    }

    @NonNull
    private BasicNavigator createNavigator() {
        return new BasicNavigator(activity, idProvider, fragmentMap, intentMap);
    }
}
