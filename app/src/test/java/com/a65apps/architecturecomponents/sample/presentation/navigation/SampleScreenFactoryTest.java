package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.Screen;
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

    @Mock
    private Screen screen;

    @Test
    public void buildTest() {
        SampleScreenFactory factory = new SampleScreenFactory();
        Fragment fragment = factory.build(new Bundle(), screen);
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(SampleFragment.class));
    }
}
