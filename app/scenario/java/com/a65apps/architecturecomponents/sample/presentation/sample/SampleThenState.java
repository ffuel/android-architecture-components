package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.a65apps.architecturecomponents.sample.presentation.common.BaseThenState;

import org.hamcrest.Description;

import androidx.annotation.NonNull;
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
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return self();
    }
}
