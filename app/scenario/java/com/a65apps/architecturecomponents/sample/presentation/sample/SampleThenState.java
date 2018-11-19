package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.a65apps.architecturecomponents.sample.domain.posts.PostsState;
import com.a65apps.architecturecomponents.sample.presentation.common.BaseThenState;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsFragment;
import com.a65apps.architecturecomponents.sample.presentation.posts.PostsPresenter;

import org.hamcrest.Description;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.espresso.matcher.BoundedMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static org.hamcrest.CoreMatchers.endsWith;

public class SampleThenState extends BaseThenState<SampleThenState> {

    @NonNull
    public SampleThenState user_should_see_loading() {
        onView(withClassName(endsWith("SwipeRefreshLayout")))
                .check(matches(new BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout.class) {
                    @Override
                    public void describeTo(Description description) {
                        description.appendText("is refreshing");
                    }

                    @Override
                    protected boolean matchesSafely(SwipeRefreshLayout item) {
                        return item.isRefreshing();
                    }
                }));

        return self();
    }

    @NonNull
    public SampleThenState wait_posts_loaded() {
        PostsPresenter presenter = getPostsPresenter();
        if (presenter == null) {
            throw new IllegalStateException("PostsPresenter is not found");
        }

        Iterable<PostsState> iterable = presenter.getInteractor().observeState().blockingIterable();
        for (PostsState state: iterable) {
            if (!state.isLoading() || !state.error().isEmpty()) {
                break;
            }
        }

        return self();
    }

    @Nullable
    private PostsPresenter getPostsPresenter() {
        FragmentManager fragmentManager = activityRule.getActivity().getSupportFragmentManager();
        PostsFragment postsFragment = null;
        for (Fragment fragment: fragmentManager.getFragments()) {
            if (fragment instanceof PostsFragment) {
                postsFragment = (PostsFragment) fragment;
                break;
            }
        }

        if (postsFragment == null) {
            return null;
        }

        return postsFragment.getPresenter();
    }
}
