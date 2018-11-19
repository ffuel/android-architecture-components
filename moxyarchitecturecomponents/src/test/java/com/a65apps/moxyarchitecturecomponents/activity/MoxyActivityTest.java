package com.a65apps.moxyarchitecturecomponents.activity;

import android.os.Bundle;

import com.a65apps.moxyarchitecturecomponents.TestApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, application = TestApp.class)
public class MoxyActivityTest {

    private ActivityController<MoxyTestActivity> activityController;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(MoxyTestActivity.class);
    }

    @Test
    public void onCreateTest() {
        MoxyTestActivity activity = activityController.create().get();

        verify(activity.getMvpDelegate()).onCreate(isNull());
    }

    @Test
    public void onCreateWithBundleTest() {
        Bundle bundle = new Bundle();
        MoxyTestActivity activity = activityController.create(bundle).get();

        verify(activity.getMvpDelegate()).onCreate(eq(bundle));
    }

    @Test
    public void onStartTest() {
        MoxyTestActivity activity = activityController.create().start().get();

        verify(activity.getMvpDelegate()).onAttach();
    }

    @Test
    public void onResumeTest() {
        MoxyTestActivity activity = activityController.create().start().resume().get();

        verify(activity.getMvpDelegate(), times(2)).onAttach();
    }

    @Test
    public void onStopTest() {
        MoxyTestActivity activity = activityController.create().start().resume().visible().pause().stop().get();

        verify(activity.getMvpDelegate()).onDetach();
    }

    @Test
    public void onSaveInstanceStateTest() {
        MoxyTestActivity activity = activityController.create().get();

        Bundle bundle = new Bundle();
        activity.onSaveInstanceState(bundle);

        verify(activity.getMvpDelegate()).onSaveInstanceState(eq(bundle));
        verify(activity.getMvpDelegate()).onDetach();
    }

    @Test
    public void onDestroyTest() {
        MoxyTestActivity activity = activityController.create().destroy().get();

        verify(activity.getMvpDelegate()).onDestroyView();
    }
}
