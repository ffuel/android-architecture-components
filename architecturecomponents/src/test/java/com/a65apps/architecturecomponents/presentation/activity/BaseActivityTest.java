package com.a65apps.architecturecomponents.presentation.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsManager;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsRequestHolder;
import com.a65apps.architecturecomponents.domain.permissions.RequestPermissionsWorker;
import com.a65apps.architecturecomponents.presentation.TestApp;
import com.a65apps.architecturecomponents.presentation.fragment.BaseFragment;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.presentation.navigation.NavigatorDelegate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, application = TestApp.class)
public class BaseActivityTest {

    @NonNull
    private final PermissionsManager permissionsManager = mock(PermissionsManager.class);
    @NonNull
    @SuppressWarnings("unchecked")
    private final StateToParcelableMapper<State, Parcelable> stateMapper = mock(StateToParcelableMapper.class);
    @NonNull
    @SuppressWarnings("unchecked")
    private final ParcelableToStateMapper<Parcelable, State> parcelMapper = mock(ParcelableToStateMapper.class);
    @NonNull
    private final NavigatorDelegate navigatorDelegate = mock(NavigatorDelegate.class);
    @NonNull
    private final PermissionsRequestHolder permissionsRequestHolder = mock(PermissionsRequestHolder.class);

    private ActivityController<TestActivity> activityController;

    @Before
    public void setup() {
        when(permissionsManager.getPermissionsRequestHolder()).thenReturn(permissionsRequestHolder);
        TestActivity.injection = activity -> {
            activity.permissionsManager = permissionsManager;
            activity.stateMapper = stateMapper;
            activity.parcelMapper = parcelMapper;
            activity.navigatorDelegate = navigatorDelegate;
        };
        activityController = Robolectric.buildActivity(TestActivity.class);
    }

    @Test
    public void onCreateTest() {
        TestActivity activity = activityController.create().get();

        verify(permissionsRequestHolder).setWorker(eq(activity));
    }

    @Test
    public void onResumeFragmentsTest() {
        activityController.create().start().resume();

        verify(navigatorDelegate).onAttach();
    }

    @Test
    public void onPauseTest() {
        activityController.create().start().resume().visible().pause();

        verify(navigatorDelegate).onDetach();
    }

    @Test
    public void onDestroyTest() {
        activityController.create().start().resume().visible().pause().stop().destroy();

        verify(permissionsRequestHolder).removeWorker();
    }

    @Test
    public void restoreStateNoSavedStateTest() {
        TestActivity activity = activityController.create().get();

        Bundle bundle = new Bundle();
        activity.restoreState(bundle);

        verify(parcelMapper, times(0)).map(any(Parcelable.class));
    }

    @Test
    public void restoreStateTest() {
        Parcelable parcelable = mock(Parcelable.class);
        State state = mock(State.class);
        when(parcelMapper.map(eq(parcelable))).thenReturn(state);
        TestActivity activity = activityController.create().get();

        Bundle bundle = new Bundle();
        bundle.putParcelable("view_state", parcelable);
        activity.restoreState(bundle);

        verify(parcelMapper).map(eq(parcelable));
        //noinspection unchecked
        verify(activity.getPresenter()).restoreState(eq(state));
    }

    @Test
    public void onSaveInstanceStateTest() {
        Parcelable parcelable = mock(Parcelable.class);
        State state = mock(State.class);
        when(stateMapper.map(eq(state))).thenReturn(parcelable);

        activityController = activityController.create().start().resume();
        TestActivity activity = activityController.get();
        activity.updateState(state);

        Bundle bundle = new Bundle();
        activityController.pause().stop().saveInstanceState(bundle);
        assertThat(bundle.getParcelable("view_state"), equalTo(parcelable));
    }

    @Test
    public void onBackPressedEmptyTest() {
        TestActivity activity = activityController.create().start().resume().get();

        activity.onBackPressed();

        verify(activity.getPresenter()).onBackPressed();
    }

    @Test
    public void onBackPressedHasFragmentTest() {
        BaseFragment fragment = mock(BaseFragment.class);
        when(fragment.onBackPressed()).thenReturn(true);
        TestActivity activity = activityController.create().start().resume().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, "tag")
                .commitNow();

        activity.onBackPressed();

        verify(fragment).onBackPressed();
        verify(activity.getPresenter(), times(0)).onBackPressed();
    }

    @Test
    public void onBackPressedHasFragmentWithContinueTest() {
        BaseFragment fragment = mock(BaseFragment.class);
        when(fragment.onBackPressed()).thenReturn(false);
        TestActivity activity = activityController.create().start().resume().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, "tag")
                .commitNow();

        activity.onBackPressed();

        verify(fragment).onBackPressed();
        verify(activity.getPresenter()).onBackPressed();
    }

    @Test
    public void onBackPressedWithTwoFragmentsTest() {
        BaseFragment fragment = mock(BaseFragment.class);
        Fragment fragment2 = mock(Fragment.class);
        when(fragment.onBackPressed()).thenReturn(false);
        TestActivity activity = activityController.create().start().resume().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, "tag")
                .commitNow();
        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment2, "tag2")
                .commitNow();

        activity.onBackPressed();

        verify(fragment, times(1)).onBackPressed();
        verify(activity.getPresenter()).onBackPressed();
    }

    @Test
    public void onBackPressedDetachedFragmentTest() {
        BaseFragment fragment = mock(BaseFragment.class);
        when(fragment.isDetached()).thenReturn(true);
        TestActivity activity = activityController.create().start().resume().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, "tag")
                .commitNow();

        activity.onBackPressed();

        verify(fragment, times(0)).onBackPressed();
        verify(activity.getPresenter()).onBackPressed();
    }

    @Test
    public void onBackPressedRemovingFragmentTest() {
        BaseFragment fragment = mock(BaseFragment.class);
        when(fragment.isRemoving()).thenReturn(true);
        TestActivity activity = activityController.create().start().resume().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, "tag")
                .commitNow();

        activity.onBackPressed();

        verify(fragment, times(0)).onBackPressed();
        verify(activity.getPresenter()).onBackPressed();
    }

    @Test
    public void onRequestPermissionsResultTest() {
        String[] permissions = new String[]{"permission"};
        int[] results = new int[] {PackageManager.PERMISSION_GRANTED};
        RequestPermissionsWorker.OnRequestPermissionCallback callback =
                mock(RequestPermissionsWorker.OnRequestPermissionCallback.class);
        TestActivity activity = activityController.create().start().resume().get();

        activity.setResultCallback(callback);
        activity.onRequestPermissionsResult(0, permissions, results);

        verify(callback).onRequestPermissionsResult(eq(0), eq(permissions), eq(PackageManager.PERMISSION_GRANTED));
    }
}
