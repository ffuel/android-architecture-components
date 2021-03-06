package com.a65apps.architecturecomponents.sample.presentation.contacts;

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

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsState;
import com.a65apps.architecturecomponents.sample.presentation.common.ButterCompositeStateFragment;
import com.a65apps.daggerarchitecturecomponents.presenter.PresenterInjector;
import com.a65apps.moxyarchitecturecomponents.view.MoxyCompositeStateView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.Collections;

import butterknife.BindView;

public class ContactsFragment extends ButterCompositeStateFragment<ContactsState, ContactsParcelable,
        ContactsListState, ContactsPresenter>
        implements MoxyCompositeStateView<ContactsState, ContactsListState> {

    public static final String SEARCH_ARG = "search_arg";

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
    public static Fragment newInstance(@NonNull Bundle bundle) {
        Fragment fragment = new ContactsFragment();
        fragment.setArguments(bundle);
        return fragment;
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
    public void updateState(@NonNull ContactsParcelable state) {
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
    public int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @NonNull
    @Override
    public ContactsPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    ContactsPresenter provideSamplePresenter() {
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalArgumentException("Can't find arguments");
        }
        String search = args.getString(SEARCH_ARG);
        if (search == null) {
            throw new IllegalArgumentException("Can't find argument " + SEARCH_ARG);
        }
        return PresenterInjector.build(ContactsPresenter.class, this, search);
    }
}
