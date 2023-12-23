package io.github.arungahlawat.automation.api.core.listeners;

import io.github.arungahlawat.automation.api.core.utils.Api;
import io.github.arungahlawat.automation.api.core.utils.FileUtils;
import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.reportUtils.AllureUtils;
import io.qameta.allure.Allure;
import org.apache.commons.lang3.StringUtils;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class TestNGListener implements IExecutionListener, IInvokedMethodListener {
    @Override
    public void onExecutionStart() {
        Log.report("Max memory: {}", Runtime.getRuntime().maxMemory());
    }

    @Override
    public void onExecutionFinish() {
        AllureUtils.copyAllureTrend();
        AllureUtils.generateEnvironmentXml(FileUtils.getConfig("environment"), new HashMap<>());
    }

    private String getTestIdentifier(IInvokedMethod method) {
        Class<?> realClass = method.getTestMethod().getRealClass();
        String packageName = StringUtils.substringAfterLast(realClass.getPackage().getName(), "tests.");
        if (StringUtils.isNotBlank(packageName))
            return packageName + "." + realClass.getSimpleName();
        return realClass.getSimpleName();
    }

    @Override
    public void beforeInvocation(final IInvokedMethod method, final ITestResult testResult) {
        if (testResult.getMethod().isBeforeClassConfiguration()) {
            Log.info("{} : Running suite setup", testResult.getMethod().getRealClass().getSimpleName());
        } else if (testResult.getMethod().isBeforeMethodConfiguration()) {
            Log.info("{} : Running test setup", testResult.getMethod().getRealClass().getSimpleName());
        } else if (testResult.getMethod().isAfterClassConfiguration()) {
            Log.info("{} : Running suite tear down", testResult.getMethod().getRealClass().getSimpleName());
        } else if (testResult.getMethod().isAfterMethodConfiguration()) {
            Log.info("{} : Running test tear down", testResult.getMethod().getRealClass().getSimpleName());
        }

        if (method.isTestMethod()) {
            String[] currentGroups = method.getTestMethod().getGroups();
            if (Arrays.asList(currentGroups).contains("UNIT-TESTS")) {
                String packageName = StringUtils.substringAfterLast(method.getTestMethod().getRealClass().getPackage().getName(), "tests.");
                Allure.epic("Unit tests");
                Allure.feature(packageName);
                Allure.story(method.getTestMethod().getRealClass().getSimpleName());
            }
            Log.info("Free memory: {}", Runtime.getRuntime().freeMemory());
            Log.info("Started: {} : {}", getTestIdentifier(method), testResult.getMethod().getDescription());
        }
    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult) {
        if (testResult.getStatus() == 2) {
            Log.error("Failed: {} : {}", getTestIdentifier(method), StringUtils.isBlank(testResult.getMethod().getDescription()) ? testResult.getMethod().getMethodName() : testResult.getMethod().getDescription());
            try {
                String traceFilePath = Api.logNetworkTraceOnFailure(Thread.currentThread().getName(), testResult.getMethod().getQualifiedName());
                Log.info("Network trace : {}", traceFilePath);
                Allure.addAttachment("Network Trace", "application/json", Files.newInputStream(Paths.get(traceFilePath)), "json");
            } catch (Exception e) {
                Log.error("exception adding attachment",e);
            }
        } else if (testResult.getStatus() == 1 && method.isTestMethod())
            Log.info("Finished: {} : {}", getTestIdentifier(method), testResult.getMethod().getDescription());
    }
}
