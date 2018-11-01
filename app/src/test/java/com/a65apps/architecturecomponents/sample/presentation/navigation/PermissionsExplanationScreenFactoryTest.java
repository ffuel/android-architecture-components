package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.sample.domain.navigation.PermissionExplanationScreen;
import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PermissionsExplanationScreenFactoryTest {

    @Mock
    private Bundle bundle;

    @Test
    public void buildTest() {
        PermissionsExplanationScreenFactory factory = new PermissionsExplanationScreenFactory();
        Fragment fragment = factory.build(bundle, PermissionExplanationScreen.create(
                Collections.singletonList("test")));
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(PermissionsExplanationFragment.class));
        verify(bundle, times(1))
                .putStringArray(anyString(), eq(new String[] {"test"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildIllegalScreenTest() {
        PermissionsExplanationScreenFactory factory = new PermissionsExplanationScreenFactory();
        factory.build(bundle, new BasicScreen("", null));
    }
}
