package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.sample.presentation.posts.PostsFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PostsScreenFactoryTest {

    @Test
    public void buildTest() {
        PostsScreenFactory factory = new PostsScreenFactory();
        Fragment fragment = factory.build(new Bundle(), null);
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(PostsFragment.class));
    }
}
