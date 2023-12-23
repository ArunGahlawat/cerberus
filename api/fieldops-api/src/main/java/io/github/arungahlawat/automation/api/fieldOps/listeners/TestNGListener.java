package io.github.arungahlawat.automation.api.fieldOps.listeners;

import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.reportUtils.AllureUtils;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;

public class TestNGListener implements ITestListener, IExecutionListener {

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onExecutionFinish() {
        AllureUtils.copyAllureTrend();
        AllureUtils.generateEnvironmentXml(FileUtils.getConfig("environment"), new HashMap<>());
    }
}
