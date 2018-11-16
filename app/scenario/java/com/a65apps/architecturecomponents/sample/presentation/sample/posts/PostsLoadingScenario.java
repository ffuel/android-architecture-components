package com.a65apps.architecturecomponents.sample.presentation.sample.posts;

import com.a65apps.architecturecomponents.sample.presentation.common.BaseScenario;
import com.a65apps.architecturecomponents.sample.presentation.common.Story;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleGivenState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleThenState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleWhenState;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingPolicies;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PostsLoadingScenario extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("posts")
    public void user_click_posts_should_open_posts_screen() {
        IdlingPolicies.setMasterPolicyTimeoutWhenDebuggerAttached(false);
        given().first_start_with_success()
                .and().server_response_$_with_body_$(200, "posts.json");

        when().user_click_posts();

        then().user_sees_text_$_in_posts_list_at_position_$("Загрузка", 0)
                .and().wait_posts_loaded()
                .and().then().user_sees_text_$_in_posts_list_at_position_$("Ruby", 0)
                .and().user_sees_text_$_in_posts_list_at_position_$("Ruby is a scripting language designed for " +
                "simplified object-oriented programming.", 0)
                .and().user_not_sees_item_in_posts_list_at_position_$(1);
    }
}
