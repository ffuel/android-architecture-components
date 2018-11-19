package com.a65apps.architecturecomponents.presentation.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class FragmentDelegateTest {

    @SuppressWarnings("unchecked")
    private final FragmentDelegate.FragmentInterface<Parcelable, Presenter> fragment
            = mock(FragmentDelegate.FragmentInterface.class);
    @SuppressWarnings("unchecked")
    private final StateToParcelableMapper<State, Parcelable> stateMapper = mock(StateToParcelableMapper.class);
    @SuppressWarnings("unchecked")
    private final ParcelableToStateMapper<Parcelable, State> parcelMapper = mock(ParcelableToStateMapper.class);
    private final Presenter presenter = mock(Presenter.class);

    private FragmentDelegate<State, Parcelable, Presenter> delegate;

    @Before
    public void setup() {
        when(fragment.getPresenter()).thenReturn(presenter);
        when(fragment.getLayoutRes()).thenReturn(0);
        delegate = createDelegate();
    }

    @Test
    public void onActivityCreatedEmptyStateTest() {
        delegate.onActivityCreated(null);

        //noinspection unchecked
        verify(fragment.getPresenter(), times(0)).restoreState(any());
    }

    @Test
    public void onActivityCreatedTest() {
        Parcelable parcelable = mock(Parcelable.class);
        State state = mock(State.class);
        when(parcelMapper.map(eq(parcelable))).thenReturn(state);
        Bundle bundle = new Bundle();
        bundle.putParcelable("view_state", parcelable);

        delegate.onActivityCreated(bundle);

        //noinspection unchecked
        verify(fragment.getPresenter()).restoreState(eq(state));
    }

    @Test
    public void updateStateTest() {
        Parcelable parcelable = mock(Parcelable.class);
        State state = mock(State.class);
        when(stateMapper.map(eq(state))).thenReturn(parcelable);

        delegate.updateState(state);

        verify(fragment).updateState(eq(parcelable));
    }

    @Test
    public void onBackPressedTest() {
        delegate.onBackPressed();

        verify(presenter).onBackPressed();
    }

    @NonNull
    private FragmentDelegate<State, Parcelable, Presenter> createDelegate() {
        return new FragmentDelegate<>(fragment, stateMapper, parcelMapper);
    }
}
