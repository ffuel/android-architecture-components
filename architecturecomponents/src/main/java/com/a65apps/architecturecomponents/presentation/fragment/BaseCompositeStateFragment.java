package com.a65apps.architecturecomponents.presentation.fragment;

import android.os.Parcelable;
import android.support.annotation.UiThread;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.CompositeStatePresenter;
import com.a65apps.architecturecomponents.presentation.view.CompositeStateView;

@UiThread
public abstract class BaseCompositeStateFragment<S extends State, Parcel extends Parcelable,
        CS extends State, P extends CompositeStatePresenter>
        extends BaseFragment<S, Parcel, P> implements CompositeStateView<S, CS> {
}
