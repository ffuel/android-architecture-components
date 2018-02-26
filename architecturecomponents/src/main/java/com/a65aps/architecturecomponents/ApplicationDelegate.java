package com.a65aps.architecturecomponents;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.a65aps.architecturecomponents.presentation.presenter.BasePresenter;
import com.a65aps.architecturecomponents.presentation.presenter.HasPresenterSubComponentBuilders;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterComponentBuilder;
import com.a65aps.architecturecomponents.presentation.presenter.PresenterSubComponentBuilderFactory;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static android.util.Log.DEBUG;
import static dagger.internal.Preconditions.checkNotNull;

public abstract class ApplicationDelegate<Component extends ApplicationDelegateComponent>
        implements HasActivityInjector, HasPresenterSubComponentBuilders {

    private static final String TAG = "dagger.android";

    @NonNull
    private static WeakReference<ApplicationDelegate> delegate = new WeakReference<>(null);

    @Inject
    @Nullable
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    @Nullable
    PresenterSubComponentBuilderFactory componentFactory;

    @Nullable
    private Component appComponent;

    public static <Component extends ApplicationDelegateComponent> void initDelegate(
            @NonNull ApplicationDelegate<Component> delegate) {
        ApplicationDelegate.delegate = new WeakReference<>(delegate);
    }

    @NonNull
    public static HasPresenterSubComponentBuilders presenterSubComponentBuilders() {
        ApplicationDelegate appDelegate = delegate.get();
        if (appDelegate == null) {
            throw new IllegalStateException("Application is not initialized");
        }

        return appDelegate;
    }

    public static void inject(Activity activity) {
        checkNotNull(activity, "activity");
        ApplicationDelegate appDelegate = delegate.get();
        if (appDelegate == null) {
            throw new IllegalStateException("Application is not initialized");
        }

        AndroidInjector<Activity> activityInjector =
                ((HasActivityInjector) appDelegate).activityInjector();
        checkNotNull(activityInjector, "%s.activityInjector() returned null", appDelegate.getClass());

        activityInjector.inject(activity);
    }

    public static void inject(Fragment fragment) {
        checkNotNull(fragment, "fragment");
        HasSupportFragmentInjector hasSupportFragmentInjector = findHasFragmentInjector(fragment);
        if (Log.isLoggable(TAG, DEBUG)) {
            Log.d(
                    TAG,
                    String.format(
                            "An injector for %s was found in %s",
                            fragment.getClass().getCanonicalName(),
                            hasSupportFragmentInjector.getClass().getCanonicalName()));
        }

        AndroidInjector<Fragment> fragmentInjector =
                hasSupportFragmentInjector.supportFragmentInjector();
        checkNotNull(
                fragmentInjector,
                "%s.supportFragmentInjector() returned null",
                hasSupportFragmentInjector.getClass());

        fragmentInjector.inject(fragment);
    }

    private static HasSupportFragmentInjector findHasFragmentInjector(Fragment fragment) {
        Fragment parentFragment = fragment;
        while ((parentFragment = parentFragment.getParentFragment()) != null) {
            if (parentFragment instanceof HasSupportFragmentInjector) {
                return (HasSupportFragmentInjector) parentFragment;
            }
        }
        Activity activity = fragment.getActivity();
        if (activity instanceof HasSupportFragmentInjector) {
            return (HasSupportFragmentInjector) activity;
        }
        if (activity != null && activity.getApplication() instanceof HasSupportFragmentInjector) {
            return (HasSupportFragmentInjector) activity.getApplication();
        }
        throw new IllegalArgumentException(
                String.format("No injector was found for %s", fragment.getClass().getCanonicalName()));
    }

    @NonNull
    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends BasePresenter> presenterClass) {
        if (componentFactory == null) {
            init();
        }

        return componentFactory.get(presenterClass);
    }

    @Override
    @Nullable
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    public void onCreate() {
        init();
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    protected abstract Component initInjector();

    private void init() {
        appComponent = initInjector();
        appComponent.inject(this);
    }

    @Nullable
    public Component getAppComponent() {
        return appComponent;
    }
}
