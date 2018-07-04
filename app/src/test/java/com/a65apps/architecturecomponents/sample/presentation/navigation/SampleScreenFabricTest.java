package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.sample.presentation.sample.SampleFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SampleScreenFabricTest {

    @Test
    public void buildTest() {
        SampleScreenFabric fabric = new SampleScreenFabric();
        Fragment fragment = fabric.build(new Bundle(), null);
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(SampleFragment.class));
    }
}
