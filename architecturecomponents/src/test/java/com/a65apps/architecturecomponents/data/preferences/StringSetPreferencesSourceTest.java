package com.a65apps.architecturecomponents.data.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringSetPreferencesSourceTest {

    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;

    @Before
    public void setup() {
        when(sharedPreferences.edit()).thenReturn(editor);
    }

    @Test
    public void performPutDataTest() {
        MockSource source = new MockSource(sharedPreferences);
        TestObserver observer = new TestObserver();

        Set<String> data = new HashSet<>();
        data.add("test1");
        data.add("test2");
        source.putData(data).subscribe(observer);

        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1))
                .putStringSet(eq("key"), eq(data));
    }

    @Test
    public void performGetDataTest() {
        MockSource source = new MockSource(sharedPreferences);
        TestObserver<Set<String>> observer = new TestObserver<>();

        source.data().subscribe(observer);

        verify(sharedPreferences, times(1))
                .getStringSet(eq("key"), eq(Collections.emptySet()));
    }

    private static final class MockSource extends StringSetPreferencesSource {

        MockSource(@NonNull SharedPreferences sharedPreferences) {
            super(sharedPreferences);
        }

        @NonNull
        @Override
        public String key() {
            return "key";
        }
    }
}
