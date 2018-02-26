package com.a65apps.architecturecomponents.presentation.sample;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.sample.SampleInteractor;
import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65apps.architecturecomponents.presentation.main.MainRouter;
import com.a65aps.architecturecomponents.presentation.fragment.BaseFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;

public class SampleFragment extends BaseFragment<SampleState, SampleParcelable, SampleView,
        SampleInteractor, MainRouter, SamplePresenter> implements SampleView {

    @InjectPresenter
    SamplePresenter presenter;

    @BindView(R.id.text)
    TextView text;

    @NonNull
    public static Fragment newInstance() {
        return new SampleFragment();
    }

    @Override
    protected void updateState(@NonNull SampleParcelable state) {
        text.setText(state.text());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_sample;
    }

    @NonNull
    @Override
    protected SamplePresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    SamplePresenter provideSamplePresenter() {
        return providePresenter(SamplePresenter.class);
    }
}
