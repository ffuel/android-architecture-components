package com.a65apps.architecturecomponents.sample.presentation.permissions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a65apps.architecturecomponents.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PermissionsExplanationFragment extends Fragment {

    private static final String MESSAGES_ARG = "messages_arg";

    @Nullable
    private Unbinder unbinder;

    @Nullable
    private String[] messages;

    @Nullable
    private PermissionsExplanationListener listener;

    @BindView(R.id.messages)
    TextView messagesView;

    @NonNull
    public static Fragment newInstance(Bundle bundle, @NonNull String... messages) {
        Fragment fragment = new PermissionsExplanationFragment();
        bundle.putStringArray(MESSAGES_ARG, messages);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PermissionsExplanationListener) {
            listener = (PermissionsExplanationListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            messages = args.getStringArray(MESSAGES_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permissions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        if (messages != null) {
            messagesView.setText(TextUtils.join("/n/n", messages));
        }
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @OnClick(R.id.permission_btn)
    void onButtonClick() {
        if (listener != null) {
            listener.onUserAgree();
        }
    }
}
