package com.a65apps.architecturecomponents.sample;

import android.Manifest;
import android.os.Environment;

import com.tngtech.jgiven.impl.Config;
import com.tngtech.jgiven.impl.ScenarioBase;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;

import androidx.test.rule.GrantPermissionRule;

public class AndroidJGivenTestRule implements TestRule {
    private final GrantPermissionRule grantPermissionRule = GrantPermissionRule
            .grant(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

    public AndroidJGivenTestRule(ScenarioBase scenario) {
        scenario.setStageClassCreator(new AndroidStageClassCreator());

        File reportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "jgiven-results/testDebugUnitTest").getAbsoluteFile();
        Config.config().setReportDir(reportDir);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return grantPermissionRule.apply(base, description);
    }
}
