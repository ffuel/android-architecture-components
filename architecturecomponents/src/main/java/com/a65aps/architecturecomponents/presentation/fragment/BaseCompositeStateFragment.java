package com.a65aps.architecturecomponents.presentation.fragment;

import android.os.Parcelable;
import android.support.annotation.UiThread;

import com.a65aps.architecturecomponents.domain.CompositeStateInteractor;
import com.a65aps.architecturecomponents.domain.State;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.architecturecomponents.presentation.presenter.CompositeStatePresenter;
import com.a65aps.architecturecomponents.presentation.view.CompositeStateView;

@UiThread
public abstract class BaseCompositeStateFragment<S extends State, Parcel extends Parcelable,
        CS extends State, V extends CompositeStateView<S, CS>,
        I extends CompositeStateInteractor<S, CS, R>, R extends Router,
        P extends CompositeStatePresenter<S, CS, V, I, R>>
        extends BaseFragment<S, Parcel, V, I, R, P> implements CompositeStateView<S, CS> {
}