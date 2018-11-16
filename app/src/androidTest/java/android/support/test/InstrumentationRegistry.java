package android.support.test;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

// TODO Workaround for mockito with androidx. Remove this when mockito remove deprecated android.support.test.InstrumentationRegistry
public class InstrumentationRegistry {

    public static Context getTargetContext() {
        return ApplicationProvider.getApplicationContext();
    }
}
