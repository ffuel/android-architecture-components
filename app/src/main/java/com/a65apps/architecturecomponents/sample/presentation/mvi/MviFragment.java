package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviState;
import com.a65apps.architecturecomponents.sample.presentation.common.ButterFragment;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.a65apps.moxyarchitecturecomponents.view.MoxyView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;

public class MviFragment extends ButterFragment<MviState, MviParcelable, MviPresenter>
        implements MoxyView<MviState> {

    @InjectPresenter
    MviPresenter presenter;

    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.subtitle)
    TextView subtitleView;

    @NonNull
    public static Fragment newInstance() {
        return new MviFragment();
    }

    @Override
    public void updateState(@NonNull MviParcelable state) {
        titleView.setText(state.title());
        subtitleView.setText(state.subtitle());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_mvi;
    }

    @NonNull
    @Override
    public MviPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    MviPresenter provideMviPresenter() {
        return PresenterInjector.build(MviPresenter.class, this);
    }
}
