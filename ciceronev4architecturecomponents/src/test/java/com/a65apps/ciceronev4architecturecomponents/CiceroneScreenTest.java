package com.a65apps.ciceronev4architecturecomponents;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.IntentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CiceroneScreenTest {

    @Mock
    private Map<String, FragmentFactory> fragmentMap;
    @Mock
    private Map<String, IntentFactory> intentMap;
    @Mock
    private FragmentFactory fragmentFactory;
    @Mock
    private IntentFactory intentFactory;
    @Mock
    private Screen screen;
    @Mock
    private Fragment fragment;
    @Mock
    private Context context;
    @Mock
    private Intent intent;

    @Before
    public void setup() {
        when(screen.getScreenKey()).thenReturn("test");
    }

    @Test
    public void getScreenKeyTest() {
        CiceroneScreen ciceroneScreen = createScreen();

        assertThat(ciceroneScreen.getScreenKey(), equalTo("test"));
    }

    @Test
    public void getFragmentTest() {
        when(fragmentMap.get(eq("test"))).thenReturn(fragmentFactory);
        when(fragmentFactory.build(any(), eq(screen))).thenReturn(fragment);
        CiceroneScreen ciceroneScreen = createScreen();

        assertThat(ciceroneScreen.getFragment(), equalTo(fragment));
        verify(fragmentFactory).build(any(), eq(screen));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFragmentNotFoundTest() {
        when(fragmentMap.get(eq("test"))).thenReturn(null);
        CiceroneScreen ciceroneScreen = createScreen();

        ciceroneScreen.getFragment();
    }

    @Test
    public void getActivityIntentTest() {
        when(intentMap.get(eq("test"))).thenReturn(intentFactory);
        when(intentFactory.build(eq(context), any(), eq(screen))).thenReturn(intent);
        CiceroneScreen ciceroneScreen = createScreen();

        assertThat(ciceroneScreen.getActivityIntent(context), equalTo(intent));
        verify(intentFactory).build(eq(context), any(), eq(screen));
    }

    @Test
    public void getActivityIntentNotFoundTest() {
        when(intentMap.get(eq("test"))).thenReturn(null);
        CiceroneScreen ciceroneScreen = createScreen();

        assertThat(ciceroneScreen.getActivityIntent(context), equalTo(null));
    }

    @Test
    public void getActivityIntentNullTest() {
        when(intentMap.get(eq("test"))).thenReturn(intentFactory);
        when(intentFactory.build(eq(context), any(), eq(screen))).thenReturn(null);
        CiceroneScreen ciceroneScreen = createScreen();

        assertThat(ciceroneScreen.getActivityIntent(context), equalTo(null));
    }

    @NonNull
    private CiceroneScreen createScreen() {
        return new CiceroneScreen(fragmentMap, intentMap, screen);
    }
}
