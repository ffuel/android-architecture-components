package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.IntentFactory;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CiceroneRouterTest {

    @Mock
    private Map<String, FragmentFactory> fragmentMap;
    @Mock
    private Map<String, IntentFactory> intentMap;
    @Mock
    private Screen screen1;
    @Mock
    private Screen screen2;

    @Test
    public void newChainTest() {
        CiceroneRouter router = createRouter();

        router.newChain(screen1, screen2);

        verify(router).newChain(any(ru.terrakok.cicerone.Screen.class));
        clearInvocations(router);

        router.newChain(screen1);
        verify(router).newChain(any(ru.terrakok.cicerone.Screen.class));
    }

    @Test
    public void newChainEmptyTest() {
        CiceroneRouter router = createRouter();

        router.newChain(new Screen[]{});

        verify(router, times(0)).newChain(any(ru.terrakok.cicerone.Screen.class));
    }

    @Test
    public void newRootChainTest() {
        CiceroneRouter router = createRouter();

        router.newRootChain(screen1, screen2);

        verify(router).newRootChain(any(ru.terrakok.cicerone.Screen.class));
        clearInvocations(router);

        router.newRootChain(screen1);
        verify(router).newRootChain(any(ru.terrakok.cicerone.Screen.class));
    }

    @Test
    public void newRootChainEmptyTest() {
        CiceroneRouter router = createRouter();

        router.newRootChain(new Screen[]{});

        verify(router, times(0)).newRootChain(any(ru.terrakok.cicerone.Screen.class));
    }

    @Test
    public void navigateToTest() {
        Screen screen = mock(Screen.class);
        CiceroneRouter router = createRouter();

        router.navigateTo(screen);

        verify(router).navigateTo(any(CiceroneScreen.class));
    }

    @Test
    public void newRootScreenTest() {
        Screen screen = mock(Screen.class);
        CiceroneRouter router = createRouter();

        router.newRootScreen(screen);

        verify(router).newRootScreen(any(CiceroneScreen.class));
    }

    @Test
    public void replaceScreenTest() {
        Screen screen = mock(Screen.class);
        CiceroneRouter router = createRouter();

        router.replaceScreen(screen);

        verify(router).replaceScreen(any(CiceroneScreen.class));
    }

    @Test
    public void backToTest() {
        Screen screen = mock(Screen.class);
        CiceroneRouter router = createRouter();

        router.backTo(screen);

        verify(router).backTo(any(CiceroneScreen.class));
    }

    @Test
    public void navigateToCompatTest() {
        CiceroneRouter router = createRouter();

        router.navigateTo("test");

        verify(router).navigateTo(any(BasicScreen.class));
    }

    @Test
    public void navigateToWithParamCompatTest() {
        CiceroneRouter router = createRouter();

        router.navigateTo("test", new Object());

        verify(router).navigateTo(any(BasicScreen.class));
    }

    @Test
    public void replaceCompatTest() {
        CiceroneRouter router = createRouter();

        router.replaceScreen("test");

        verify(router).replaceScreen(any(BasicScreen.class));
    }

    @Test
    public void replaceWithParamCompatTest() {
        CiceroneRouter router = createRouter();

        router.replaceScreen("test", new Object());

        verify(router).replaceScreen(any(BasicScreen.class));
    }

    @Test
    public void backToCompatTest() {
        CiceroneRouter router = createRouter();

        router.backTo("test");

        verify(router).backTo(any(BasicScreen.class));
    }

    @Test
    public void newRootCompatTest() {
        CiceroneRouter router = createRouter();

        router.newRootScreen("test");

        verify(router).newRootScreen(any(BasicScreen.class));
    }

    @Test
    public void newRootWithParamCompatTest() {
        CiceroneRouter router = createRouter();

        router.newRootScreen("test", new Object());

        verify(router).newRootScreen(any(BasicScreen.class));
    }

    @NonNull
    private CiceroneRouter createRouter() {
        return spy(new CiceroneRouter(fragmentMap, intentMap));
    }
}
