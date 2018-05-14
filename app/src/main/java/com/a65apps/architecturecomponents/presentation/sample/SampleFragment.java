package com.a65apps.architecturecomponents.presentation.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
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
import com.a65apps.architecturecomponents.presentation.contacts.SearchContactsListener;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.data)
    TextView data;
    @BindView(R.id.contacts_btn)
    View buttonView;

    @Nullable
    private SearchContactsListener listener;

    @NonNull
    public static Fragment newInstance() {
        return new SampleFragment();
    }

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SearchContactsListener) {
            listener = (SearchContactsListener) context;
        }
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
                data.setVisibility(View.GONE);
                buttonView.setVisibility(View.GONE);
                refresh.setRefreshing(true);
                break;
            case COMPLETE:
                data.setVisibility(View.VISIBLE);
                buttonView.setVisibility(View.VISIBLE);
                refresh.setRefreshing(false);
                break;
            case ERROR:
                if (state.data().isEmpty()) {
                    data.setVisibility(View.GONE);
                } else {
                    data.setVisibility(View.VISIBLE);
                }
                buttonView.setVisibility(View.GONE);
                refresh.setRefreshing(false);
                break;

            default:
                throw new IllegalArgumentException("Wrong state type");
        }
        updateError(state);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
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
        return PresenterInjector.build(SamplePresenter.class, this);
    }

    @OnClick(R.id.contacts_btn)
    void onContactsClick() {
        if (listener != null) {
            listener.onSearchContacts();
        }
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
