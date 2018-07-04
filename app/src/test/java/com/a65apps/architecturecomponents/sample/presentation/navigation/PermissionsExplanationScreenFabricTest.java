package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.sample.presentation.permissions.PermissionsExplanationFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PermissionsExplanationScreenFabricTest {

    @Mock
    private Bundle bundle;

    @Test
    public void buildTest() {
        PermissionsExplanationScreenFabric fabric = new PermissionsExplanationScreenFabric();
        Fragment fragment = fabric.build(bundle, new String[] {"test"});
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(PermissionsExplanationFragment.class));
        verify(bundle, times(1))
                .putStringArray(anyString(), eq(new String[] {"test"}));
    }
}
