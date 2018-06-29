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
public class BooleanPreferencesSourceTest {

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

        source.putData(true).subscribe(observer);

        verify(sharedPreferences, times(1)).edit();
        verify(editor, times(1))
                .putBoolean(eq("key"), eq(true));
    }

    @Test
    public void performGetDataTest() {
        MockSource source = new MockSource(sharedPreferences);
        TestObserver<Boolean> observer = new TestObserver<>();

        source.data().subscribe(observer);

        verify(sharedPreferences, times(1))
                .getBoolean(eq("key"), eq(false));
    }

    private static final class MockSource extends BooleanPreferencesSource {

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
