package com.a65apps.ciceronearchitecturecomponents;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Forward;

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
    private Map<String, FragmentFactory> fragmentMap;
    @Mock
    private Map<String, IntentFactory> intentMap;
    @Mock
    private Map<String, NavigationInterceptor> interceptorMap;
    @Mock
    private Map<String, FragmentAnimationFactory> animationMap;
    @Mock
    private IntentFactory intentFactory;
    @Mock
    private FragmentFactory fragmentFactory;
    @Mock
    private NavigationInterceptor interceptor;
    @Mock
    private FragmentAnimationFactory animationFactory;
    @Mock
    private Context context;
    @Mock
    private Intent intent;
    @Mock
    private Fragment fragment;
    @Mock
    private FragmentTransaction fragmentTransaction;

    @Test
    public void createActivityIntentTest() {
        when(intentMap.get(eq("test")))
                .thenReturn(intentFactory);
        when(intentFactory.build(eq(context), any(), isNull()))
                .thenReturn(intent);
        BasicNavigator navigator = createNavigator();

        Intent intent = navigator.createActivityIntent(context, "test", null);
        Intent intentNull = navigator.createActivityIntent(context, "test1", null);

        assertThat(intent, equalTo(intent));
        assertTrue(intentNull == null);
        verify(intentFactory, times(1))
                .build(eq(context), any(), isNull());
    }

    @Test
    public void createFragmentTest() {
        when(fragmentMap.get(eq("test")))
                .thenReturn(fragmentFactory);
        when(fragmentFactory.build(any(), isNull()))
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

    @Test
    public void interceptorsTest() {
        when(interceptorMap.get(eq("test")))
                .thenReturn(interceptor);
        Forward forward = new Forward("test", null);
        when(interceptor.intercept(eq(activity), eq(forward)))
                .thenReturn(true);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommand(forward);

        verify(interceptor, times(1))
                .intercept(eq(activity), eq(forward));
        verify(navigator, times(0)).createFragment(any(), any());
        verify(navigator, times(0)).createActivityIntent(any(), any(), any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void interceptorsNotFoundTest() {
        when(interceptorMap.get(any(String.class)))
                .thenReturn(null);
        Forward forward = new Forward("test", null);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommand(forward);

        verify(navigator, times(1)).createFragment(any(), any());
        verify(navigator, times(1)).createActivityIntent(any(), any(), any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void interceptorsNotInterceptTest() {
        when(interceptorMap.get(eq("test")))
                .thenReturn(interceptor);
        Forward forward = new Forward("test", null);
        when(interceptor.intercept(eq(activity), eq(forward)))
                .thenReturn(false);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommand(forward);

        verify(interceptor, times(1))
                .intercept(eq(activity), eq(forward));
        verify(navigator, times(1)).createFragment(any(), any());
        verify(navigator, times(1)).createActivityIntent(any(), any(), any());
    }

    @Test
    public void setupFragmentTransactionAnimationTest() {
        when(animationMap.get(eq("Fragment Fragment Forward")))
                .thenReturn(animationFactory);
        Forward forward = new Forward("test", null);
        Fragment fragment = new Fragment();
        BasicNavigator navigator = createNavigator();

        navigator.setupFragmentTransactionAnimation(forward, fragment, fragment,
                fragmentTransaction);

        verify(animationFactory, times(1))
                .setupTransactionAnimation(eq(forward), eq(fragment), eq(fragment),
                        eq(fragmentTransaction));
    }

    @Test
    public void setupFragmentTransactionAnimationCurrentNullTest() {
        when(animationMap.get(eq("null Fragment Forward")))
                .thenReturn(animationFactory);
        Forward forward = new Forward("test", null);
        Fragment fragment = new Fragment();
        BasicNavigator navigator = createNavigator();

        navigator.setupFragmentTransactionAnimation(forward, null, fragment,
                fragmentTransaction);

        verify(animationFactory, times(1))
                .setupTransactionAnimation(eq(forward), eq(null), eq(fragment),
                        eq(fragmentTransaction));
    }

    @Test
    public void setupFragmentTransactionAnimationNextNullTest() {
        when(animationMap.get(eq("Fragment null Back")))
                .thenReturn(animationFactory);
        Back back = new Back();
        Fragment fragment = new Fragment();
        BasicNavigator navigator = createNavigator();

        navigator.setupFragmentTransactionAnimation(back, fragment, null,
                fragmentTransaction);

        verify(animationFactory, times(1))
                .setupTransactionAnimation(eq(back), eq(fragment), eq(null),
                        eq(fragmentTransaction));
    }

    @Test(expected = IllegalStateException.class)
    public void setupFragmentTransactionAnimationErrorTest() {
        BasicNavigator navigator = createNavigator();
        Forward forward = new Forward("test", null);

        navigator.setupFragmentTransactionAnimation(forward, null, null,
                fragmentTransaction);
    }

    @NonNull
    private BasicNavigator createNavigator() {
        return Mockito.spy(new BasicNavigator(activity, idProvider, fragmentMap, intentMap,
                interceptorMap, animationMap));
    }
}
