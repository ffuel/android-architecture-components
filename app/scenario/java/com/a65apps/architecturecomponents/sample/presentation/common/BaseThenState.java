package com.a65apps.architecturecomponents.sample.presentation.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.sample.presentation.main.MainActivity;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.AfterStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.attachment.MediaType;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.util.Checks.checkNotNull;

public class BaseThenState<T extends BaseThenState<T>> extends Stage<T> {

    private static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    private static Matcher<View> noItemAtPosition(final int position) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has no item at position " + position + ": ");
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                return viewHolder == null;
            }
        };
    }

    @Rule
    @ExpectedScenarioState(required = true)
    public ActivityTestRule<MainActivity> activityRule;
    @ExpectedScenarioState(required = true)
    public CurrentStep currentStep;
    @ExpectedScenarioState(required = true)
    public ScreenshotCapture screenshotCapture;

    @AfterStage
    public void afterStage() {
        ScreenshotCapture.Data data = screenshotCapture.takeScreenshot(activityRule.getActivity());
        if (data != null) {
            currentStep.addAttachment(Attachment.fromBinaryBytes(data.data, MediaType.PNG)
                    .showDirectly());
        }
    }

    @NonNull
    public T user_sees_text_$(@NonNull @Quoted String text) {
        onView(withText(text)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        return self();
    }

    @NonNull
    public T user_sees_text_$_in_contacts_list_at_position_$(@NonNull @Quoted String text, int position) {
        onView(withId(R.id.contacts)).check(matches(atPosition(position, hasDescendant(withText(text)))));
        return self();
    }

    @NonNull
    public T user_sees_text_$_in_posts_list_at_position_$(@NonNull @Quoted String text, int position) {
        onView(withId(R.id.recycler_view)).check(matches(atPosition(position, hasDescendant(withText(text)))));
        return self();
    }

    @NonNull
    public T user_not_sees_item_in_posts_list_at_position_$(int position) {
        onView(withId(R.id.recycler_view)).check(matches(noItemAtPosition(position)));
        return self();
    }
}
