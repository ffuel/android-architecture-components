package com.a65apps.architecturecomponents.presentation.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.sample.SampleInteractor;
import com.a65apps.architecturecomponents.domain.sample.SampleState;
import com.a65apps.architecturecomponents.presentation.common.ButterFragment;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;

public class SampleFragment extends ButterFragment<SampleState, SampleParcelable, SampleView,
        SampleInteractor, Router, SamplePresenter> implements SampleView,
        SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    SamplePresenter presenter;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.progress)
    View progress;
    @BindView(R.id.data)
    TextView data;

    @NonNull
    public static Fragment newInstance() {
        return new SampleFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        refresh.setOnRefreshListener(this);
    }

    @Override
    protected void updateState(@NonNull SampleParcelable state) {
        text.setText(state.text());
        data.setText(state.data());
        switch (state.state()) {
            case LOADING:
                progress.setVisibility(View.VISIBLE);
                data.setVisibility(View.GONE);
                refresh.setRefreshing(true);
                break;
            case COMPLETE:
                progress.setVisibility(View.GONE);
                data.setVisibility(View.VISIBLE);
                refresh.setRefreshing(false);
                break;
            case ERROR:
                progress.setVisibility(View.GONE);
                if (state.data().isEmpty()) {
                    data.setVisibility(View.GONE);
                } else {
                    data.setVisibility(View.VISIBLE);
                }
                refresh.setRefreshing(false);
                break;

            default:
                throw new IllegalArgumentException("Wrong state type");
        }
        updateError(state);
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
        return PresenterInjector.build(SamplePresenter.class, requireActivity().getApplication());
    }

    private void updateError(@NonNull SampleParcelable state) {
        if (state.error().isEmpty()) {
            error.setVisibility(View.GONE);
        } else {
            error.setVisibility(View.VISIBLE);
            error.setText(state.error());
        }
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }
}
