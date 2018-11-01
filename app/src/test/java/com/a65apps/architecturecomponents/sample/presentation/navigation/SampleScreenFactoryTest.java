package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
import com.a65apps.architecturecomponents.sample.domain.navigation.SampleScreen;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SampleScreenFactoryTest {

    @Test
    public void buildTest() {
        SampleScreenFactory factory = new SampleScreenFactory();
        Fragment fragment = factory.build(new Bundle(), SampleScreen.create());
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(SampleFragment.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildIllegalScreenTest() {
        SampleScreenFactory factory = new SampleScreenFactory();
        factory.build(new Bundle(), new BasicScreen("", null));
    }
}
