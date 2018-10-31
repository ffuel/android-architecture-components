package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.a65apps.architecturecomponents.presentation.navigationv2.BasicScreen;
import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ContactsScreenFactoryTest {

    @Mock
    private Bundle bundle;

    @Test
    public void buildTest() {
        ContactsScreenFactory factory = new ContactsScreenFactory();
        Fragment fragment = factory.build(bundle, new BasicScreen("", null));
        assertNotNull(fragment);
        assertThat(fragment.getClass(), equalTo(ContactsFragment.class));
        verify(bundle, times(1))
                .putString(eq(ContactsFragment.SEARCH_ARG), eq("тест"));
    }
}
