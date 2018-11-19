package com.a65apps.architecturecomponents.presentation.activity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.presenter.Presenter;

import static org.mockito.Mockito.mock;

public class TestActivity extends BaseActivity<State, Parcelable, Presenter> {

    public static Injection injection;

    private final Presenter presenter = mock(Presenter.class);

    @Override
    protected void inject() {
        if (injection != null) {
            injection.inject(this);
        }
    }

    @Override
    protected void updateState(@NonNull Parcelable state) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.test_layout;
    }

    @NonNull
    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    public interface Injection {
        void inject(@NonNull TestActivity activity);
    }
}
