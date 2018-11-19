package com.a65apps.moxyarchitecturecomponents.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.fragment.BaseFragment;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;
import com.arellomobile.mvp.MvpDelegate;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoxyFragmentDelegateTest {

    @SuppressWarnings("unchecked")
    private MvpDelegate<? extends BaseFragment<State, Parcelable, Presenter>> mvpDelegate = mock(MvpDelegate.class);

    private MoxyFragmentDelegate<State, Parcelable, Presenter> fragmentDelegate;

    @Before
    public void setup() {
        fragmentDelegate = new MoxyFragmentDelegate<>(mvpDelegate);
    }

    @Test
    public void onCreateTest() {
        Bundle bundle = new Bundle();

        fragmentDelegate.onCreate(bundle);

        verify(mvpDelegate).onCreate(eq(bundle));
    }

    @Test
    public void onAttachTest() {
        fragmentDelegate.onAttach();

        verify(mvpDelegate).onAttach();
    }

    @Test
    public void onStopTest() {
        fragmentDelegate.onStop();

        verify(mvpDelegate).onDetach();
    }

    @Test
    public void onSaveInstanceStateTest() {
        Bundle bundle = new Bundle();

        fragmentDelegate.onSaveInstanceState(bundle);

        verify(mvpDelegate).onSaveInstanceState(eq(bundle));
        verify(mvpDelegate).onDetach();
    }

    @Test
    public void onDestroyViewTest() {
        fragmentDelegate.onDestroyView();

        verify(mvpDelegate).onDetach();
        verify(mvpDelegate).onDestroyView();
    }

    @Test
    public void onDestroyActivityFinishingTest() throws Exception {
        FragmentActivity activity = mock(FragmentActivity.class);
        FragmentHostCallback hostCallback = new FragmentHostCallback(activity,
                mock(Handler.class), 0) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };

        Fragment fragment = mock(Fragment.class);
        Field field = Fragment.class.getDeclaredField("mHost");
        field.setAccessible(true);
        field.set(fragment, hostCallback);
        field.setAccessible(false);
        when(activity.isFinishing()).thenReturn(true);

        fragmentDelegate.onDestroy(fragment);

        verify(mvpDelegate).onDestroy();
    }

    @Test
    public void onDestroyIsStateSavedTest() throws Exception {
        FragmentActivity activity = mock(FragmentActivity.class);
        FragmentHostCallback hostCallback = new FragmentHostCallback(activity,
                mock(Handler.class), 0) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };

        Fragment fragment = mock(Fragment.class);
        Field field = Fragment.class.getDeclaredField("mHost");
        field.setAccessible(true);
        field.set(fragment, hostCallback);
        field.setAccessible(false);
        when(activity.isFinishing()).thenReturn(false);

        Bundle bundle = new Bundle();
        fragmentDelegate.onSaveInstanceState(bundle);
        fragmentDelegate.onDestroy(fragment);

        verify(mvpDelegate).onSaveInstanceState(eq(bundle));
        verify(mvpDelegate).onDetach();
        verify(mvpDelegate, times(0)).onDestroy();
    }

    @Test
    public void onDestroyAnyParentIsRemovingTest() throws Exception {
        FragmentActivity activity = mock(FragmentActivity.class);
        FragmentHostCallback hostCallback = new FragmentHostCallback(activity,
                mock(Handler.class), 0) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };

        Fragment parentFragment = mock(Fragment.class);
        Fragment fragment = mock(Fragment.class);
        Field field = Fragment.class.getDeclaredField("mHost");
        field.setAccessible(true);
        field.set(fragment, hostCallback);
        field.setAccessible(false);
        field = Fragment.class.getDeclaredField("mParentFragment");
        field.setAccessible(true);
        field.set(fragment, parentFragment);
        field.setAccessible(false);
        field = Fragment.class.getDeclaredField("mRemoving");
        field.setAccessible(true);
        field.set(fragment, false);
        field.set(parentFragment, true);
        field.setAccessible(false);
        when(activity.isFinishing()).thenReturn(false);

        fragmentDelegate.onDestroy(fragment);

        verify(mvpDelegate).onDestroy();
    }

    @Test
    public void onDestroyIsRemovingTest() throws Exception {
        FragmentActivity activity = mock(FragmentActivity.class);
        FragmentHostCallback hostCallback = new FragmentHostCallback(activity,
                mock(Handler.class), 0) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };

        Fragment fragment = mock(Fragment.class);
        Field field = Fragment.class.getDeclaredField("mHost");
        field.setAccessible(true);
        field.set(fragment, hostCallback);
        field.setAccessible(false);
        field = Fragment.class.getDeclaredField("mRemoving");
        field.setAccessible(true);
        field.set(fragment, true);
        field.setAccessible(false);
        when(activity.isFinishing()).thenReturn(false);

        fragmentDelegate.onDestroy(fragment);

        verify(mvpDelegate).onDestroy();
    }

    @Test
    public void onDestroyNotRemovingTest() throws Exception {
        FragmentActivity activity = mock(FragmentActivity.class);
        FragmentHostCallback hostCallback = new FragmentHostCallback(activity,
                mock(Handler.class), 0) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };

        Fragment fragment = mock(Fragment.class);
        Field field = Fragment.class.getDeclaredField("mHost");
        field.setAccessible(true);
        field.set(fragment, hostCallback);
        field.setAccessible(false);
        field = Fragment.class.getDeclaredField("mRemoving");
        field.setAccessible(true);
        field.set(fragment, false);
        field.setAccessible(false);
        when(activity.isFinishing()).thenReturn(false);

        fragmentDelegate.onDestroy(fragment);

        verify(mvpDelegate, times(0)).onDestroy();
    }
}
