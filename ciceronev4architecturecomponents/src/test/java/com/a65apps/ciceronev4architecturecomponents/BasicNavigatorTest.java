package com.a65apps.ciceronev4architecturecomponents;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;
import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.IntentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
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
    private Intent intent;
    @Mock
    private Fragment fragment;
    @Mock
    private Fragment currentFragment;
    @Mock
    private FragmentTransaction fragmentTransaction;
    @Mock
    private PackageManager packageManager;
    @Mock
    private FragmentManager fragmentManager;

    @NonNull
    private final ComponentName componentName = new ComponentName("test", "test");

    @Before
    public void setup() {
        when(idProvider.get()).thenReturn(0);
        when(activity.getPackageManager()).thenReturn(packageManager);
        when(intent.resolveActivity(eq(packageManager))).thenReturn(componentName);
        when(activity.getSupportFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        when(fragmentManager.findFragmentById(eq(0))).thenReturn(currentFragment);
    }

    @Test
    public void createActivityIntentTest() {
        Screen screen = new BasicScreen("test", null);
        when(intentMap.get(eq("test")))
                .thenReturn(intentFactory);
        when(intentFactory.build(eq(activity), any(), eq(screen)))
                .thenReturn(intent);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{new Forward(new CiceroneScreen(fragmentMap, intentMap, screen))});

        verify(intentFactory).build(eq(activity), any(), eq(screen));
        verify(activity).startActivity(eq(intent), isNull());
    }

    @Test
    public void createFragmentTest() {
        Screen screen = new BasicScreen("test", null);
        when(fragmentMap.get(eq("test")))
                .thenReturn(fragmentFactory);
        when(fragmentFactory.build(any(), eq(screen)))
                .thenReturn(fragment);
        when(fragmentTransaction.replace(eq(0), eq(fragment)))
                .thenReturn(fragmentTransaction);
        when(fragmentTransaction.addToBackStack(eq("test")))
                .thenReturn(fragmentTransaction);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{new Forward(new CiceroneScreen(fragmentMap, intentMap, screen))});

        verify(fragmentFactory).build(any(), eq(screen));
        verify(fragmentTransaction).replace(eq(0), eq(fragment));
        verify(fragmentTransaction).addToBackStack(eq("test"));
        verify(fragmentTransaction).commit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFragmentNotFoundTest() {
        Screen screen = new BasicScreen("test", null);
        BasicNavigator navigator = createNavigator();
        navigator.applyCommands(new Command[]{new Forward(new CiceroneScreen(fragmentMap, intentMap, screen))});
    }

    @Test
    public void interceptorsTest() {
        Screen screen = new BasicScreen("test", null);
        Forward forward = new Forward(new CiceroneScreen(fragmentMap, intentMap, screen));
        when(interceptorMap.get(eq("test")))
                .thenReturn(interceptor);
        when(interceptor.intercept(eq(activity), eq(forward)))
                .thenReturn(true);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{forward});

        verify(interceptor).intercept(eq(activity), eq(forward));
    }

    @Test(expected = IllegalArgumentException.class)
    public void interceptorsNotFoundTest() {
        Screen screen = new BasicScreen("test", null);
        Forward forward = new Forward(new CiceroneScreen(fragmentMap, intentMap, screen));
        when(interceptorMap.get(any(String.class)))
                .thenReturn(null);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{forward});
    }

    @Test(expected = IllegalArgumentException.class)
    public void interceptorsNotInterceptTest() {
        Screen screen = new BasicScreen("test", null);
        Forward forward = new Forward(new CiceroneScreen(fragmentMap, intentMap, screen));
        when(interceptorMap.get(eq("test")))
                .thenReturn(interceptor);
        when(interceptor.intercept(eq(activity), eq(forward)))
                .thenReturn(false);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{forward});

        verify(interceptor).intercept(eq(activity), eq(forward));
    }

    @Test
    public void setupFragmentTransactionAnimationTest() {
        Screen screen = new BasicScreen("test", null);
        Forward forward = new Forward(new CiceroneScreen(fragmentMap, intentMap, screen));
        Fragment fragment = new Fragment();
        Fragment currentFragment = new Fragment();
        when(fragmentManager.findFragmentById(eq(0))).thenReturn(currentFragment);
        when(animationMap.get(eq("Fragment Fragment Forward")))
                .thenReturn(animationFactory);
        when(fragmentMap.get(eq("test")))
                .thenReturn(fragmentFactory);
        when(fragmentFactory.build(any(), eq(screen)))
                .thenReturn(fragment);
        when(fragmentTransaction.replace(eq(0), eq(fragment)))
                .thenReturn(fragmentTransaction);
        when(fragmentTransaction.addToBackStack(eq("test")))
                .thenReturn(fragmentTransaction);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{forward});

        verify(animationFactory).setupTransactionAnimation(eq(forward), eq(currentFragment), eq(fragment),
                eq(fragmentTransaction));
    }

    @Test
    public void setupFragmentTransactionAnimationCurrentNullTest() {
        Screen screen = new BasicScreen("test", null);
        Forward forward = new Forward(new CiceroneScreen(fragmentMap, intentMap, screen));
        Fragment fragment = new Fragment();
        when(fragmentManager.findFragmentById(eq(0))).thenReturn(null);
        when(animationMap.get(eq("null Fragment Forward")))
                .thenReturn(animationFactory);
        when(fragmentMap.get(eq("test")))
                .thenReturn(fragmentFactory);
        when(fragmentFactory.build(any(), eq(screen)))
                .thenReturn(fragment);
        when(fragmentTransaction.replace(eq(0), eq(fragment)))
                .thenReturn(fragmentTransaction);
        when(fragmentTransaction.addToBackStack(eq("test")))
                .thenReturn(fragmentTransaction);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{forward});

        verify(animationFactory).setupTransactionAnimation(eq(forward), eq(null), eq(fragment),
                eq(fragmentTransaction));
    }

    @Test(expected = RuntimeException.class)
    public void nullFragmentErrorTest() {
        Screen screen = new BasicScreen("test", null);
        Forward forward = new Forward(new CiceroneScreen(fragmentMap, intentMap, screen));
        when(fragmentMap.get(eq("test")))
                .thenReturn(fragmentFactory);
        when(fragmentFactory.build(any(), eq(screen)))
                .thenReturn(null);
        BasicNavigator navigator = createNavigator();

        navigator.applyCommands(new Command[]{forward});
    }

    @NonNull
    private BasicNavigator createNavigator() {
        return Mockito.spy(new BasicNavigator(activity, idProvider, interceptorMap, animationMap));
    }
}
