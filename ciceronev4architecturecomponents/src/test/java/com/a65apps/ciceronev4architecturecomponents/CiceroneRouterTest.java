package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;

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
import static org.mockito.Mockito.spy;
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
    public void newRootChainTest() {
        CiceroneRouter router = createRouter();

        router.newRootChain(screen1, screen2);

        verify(router).newRootChain(any(ru.terrakok.cicerone.Screen.class));
        clearInvocations(router);

        router.newRootChain(screen1);
        verify(router).newRootChain(any(ru.terrakok.cicerone.Screen.class));
    }

    @NonNull
    private CiceroneRouter createRouter() {
        return spy(new CiceroneRouter(fragmentMap, intentMap));
    }
}
