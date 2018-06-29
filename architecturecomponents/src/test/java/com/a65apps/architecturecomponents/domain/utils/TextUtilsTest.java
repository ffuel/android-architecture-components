package com.a65apps.architecturecomponents.domain.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TextUtilsTest {

    @Test
    public void isEmptyTest() {
        assertThat(TextUtils.isEmpty(null), equalTo(true));
        assertThat(TextUtils.isEmpty(""), equalTo(true));
        assertThat(TextUtils.isEmpty("test"), equalTo(false));
        assertThat(TextUtils.isEmpty("t"), equalTo(false));
    }
}
