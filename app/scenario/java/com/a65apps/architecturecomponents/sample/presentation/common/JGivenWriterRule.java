package com.a65apps.architecturecomponents.sample.presentation.common;

import com.tngtech.jgiven.junit.ScenarioModelHolder;
import com.tngtech.jgiven.report.impl.CommonReportHelper;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class JGivenWriterRule extends TestWatcher {

    private CommonReportHelper commonReportHelper;

    public JGivenWriterRule() {
        this.commonReportHelper = new CommonReportHelper();
    }

    public CommonReportHelper getCommonReportHelper() {
        return commonReportHelper;
    }

    @Override
    protected void finished(Description description) {
        writeReport(description);
    }

    private void writeReport(Description description) {
        commonReportHelper.finishReport(ScenarioModelHolder.getInstance()
                .getAndRemoveReportModel(description.getTestClass()));
    }
}
