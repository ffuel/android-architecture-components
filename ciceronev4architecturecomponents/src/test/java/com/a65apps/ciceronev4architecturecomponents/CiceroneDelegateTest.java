package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CiceroneDelegateTest {

    @Mock
    private NavigatorHolder navigatorHolder;
    @Mock
    private Navigator navigator;

    @Test
    public void onAttachTest() {
        CiceroneDelegate delegate = createDelegate();

        delegate.onAttach();

        verify(navigatorHolder).setNavigator(eq(navigator));
    }

    @Test
    public void onDetachTest() {
        CiceroneDelegate delegate = createDelegate();

        delegate.onDetach();

        verify(navigatorHolder).removeNavigator();
    }

    @NonNull
    private CiceroneDelegate createDelegate() {
        return new CiceroneDelegate(navigatorHolder, navigator);
    }
}
