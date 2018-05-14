package com.a65apps.architecturecomponents.presentation.contacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.contacts.ContactsInteractor;
import com.a65apps.architecturecomponents.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.presentation.common.ButterCompositeStateFragment;
import com.a65aps.architecturecomponents.presentation.navigation.Router;
import com.a65aps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.a65aps.moxyarchitecturecomponents.view.MoxyCompositeStateView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Collections;

import butterknife.BindView;

public class ContactsFragment extends ButterCompositeStateFragment<ContactsState, ContactsParcelable,
        ContactsListState, MoxyCompositeStateView<ContactsState, ContactsListState>, ContactsInteractor,
        Router, ContactsPresenter>
        implements MoxyCompositeStateView<ContactsState, ContactsListState> {

    @InjectPresenter
    ContactsPresenter presenter;

    @BindView(R.id.search)
    EditText searchView;
    @BindView(R.id.contacts)
    RecyclerView contactsView;

    @Nullable
    private ContactsAdapter adapter;

    private final TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//          Unused method
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//          Unused method
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }

            String query = s.toString();
            presenter.query(query);
        }
    };

    @NonNull
    public static Fragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        searchView.addTextChangedListener(searchWatcher);
        adapter = new ContactsAdapter(Collections.emptyList());
        contactsView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        contactsView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        searchView.removeTextChangedListener(searchWatcher);
        adapter = null;
        super.onDestroyView();
    }

    @Override
    protected void updateState(@NonNull ContactsParcelable state) {
        if (!state.query().equals(searchView.getText().toString())) {
            searchView.removeTextChangedListener(searchWatcher);
            searchView.setText(state.query());
            searchView.addTextChangedListener(searchWatcher);
        }
    }

    @Override
    public void updateDependentState(@NonNull ContactsListState state) {
        if (adapter != null) {
            adapter.updateContacts(state.contacts());
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @NonNull
    @Override
    protected ContactsPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    ContactsPresenter provideSamplePresenter() {
        return PresenterInjector.build(ContactsPresenter.class, this);
    }
}
