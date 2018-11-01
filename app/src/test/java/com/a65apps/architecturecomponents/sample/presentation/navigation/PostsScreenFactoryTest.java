package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PostsScreen;
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
        Fragment fragment = factory.build(new Bundle(), PostsScreen.create());
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(PostsFragment.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildIllegalScreenTest() {
        PostsScreenFactory factory = new PostsScreenFactory();
        factory.build(new Bundle(), new BasicScreen("", null));
    }
}
