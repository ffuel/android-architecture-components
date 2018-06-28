package com.a65apps.architecturecomponents.data.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringPreferencesSourceTest {

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

        source.putData("test").subscribe(observer);

        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1))
                .putString(eq("key"), eq("test"));
    }

    @Test
    public void performGetDataTest() {
        MockSource source = new MockSource(sharedPreferences);
        TestObserver<String> observer = new TestObserver<>();

        source.data().subscribe(observer);

        verify(sharedPreferences, times(1))
                .getString(eq("key"), eq(""));
    }

    private static final class MockSource extends StringPreferencesSource {

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
