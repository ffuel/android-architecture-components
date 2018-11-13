package android.support.test;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

public class InstrumentationRegistry {

    public static Context getTargetContext() {
        return ApplicationProvider.getApplicationContext();
    }
}
