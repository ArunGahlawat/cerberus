package io.github.arungahlawat.automation.core.utils.reportUtils;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.impl.writers.XmlWriter;
import io.qameta.allure.util.PropertiesUtils;
import org.apache.commons.configuration2.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class AllureUtils {
    private static final Map<PathType, String> ALLURE_PATHS = new HashMap<>();
    private static boolean isInit = false;

    private static void initAllurePaths() {
        if (!isInit) {
            isInit = true;
            final Properties properties = PropertiesUtils.loadAllureProperties();
            ALLURE_PATHS.put(PathType.REPORT_PATH, properties.getProperty("allure.report.directory", "test-report"));
            ALLURE_PATHS.put(PathType.RESULTS_PATH, properties.getProperty("allure.results.directory", FileUtils.transformFilePath("target/allure-results")));
        }
    }

    public static void copyAllureTrend() {
        initAllurePaths();
        copyAllureTrend(ALLURE_PATHS.get(PathType.REPORT_PATH), ALLURE_PATHS.get(PathType.RESULTS_PATH));
    }

    public static void copyAllureTrend(String reportDirectory, String resultsDirectory) {
        Log.debug("Copying allure history trends from {} to {}", reportDirectory, resultsDirectory);
        try {
            FileUtils.copyDirectoryToDirectory(
                    new File(reportDirectory + "/history"),
                    new File(resultsDirectory));
        } catch (IOException e) {
            Log.warn("Exception in copying history trend. {}", e.getMessage());
        }
    }

    public static void generateEnvironmentXml(Configuration configuration, Map<String, String> additionalParameters) {
        initAllurePaths();
        generateEnvironmentXml(ALLURE_PATHS.get(PathType.RESULTS_PATH), configuration, additionalParameters);
    }

    public static void generateEnvironmentXml(String resultsDirectory, Configuration configuration, Map<String, String> additionalParameters) {
        Map<String, String> parameters = new LinkedHashMap<>();
        configuration.getKeys().forEachRemaining(key -> parameters.put(key, configuration.getString(key)));
        parameters.putAll(additionalParameters);
        String xmlFilePath = FileUtils.transformFilePath(resultsDirectory + "/environment.xml");
        Document document = XmlWriter.getInstance().getDocument();
        Element root = document.createElement("environment");
        document.appendChild(root);
        for (String parameterKey : parameters.keySet()) {
            Element parameter = document.createElement("parameter");
            root.appendChild(parameter);
            Element key = document.createElement("key");
            key.appendChild(document.createTextNode(parameterKey));
            parameter.appendChild(key);
            Element value = document.createElement("value");
            value.appendChild(document.createTextNode(parameters.get(parameterKey)));
            parameter.appendChild(value);
        }
        XmlWriter.getInstance().write(document, xmlFilePath);
    }

    enum PathType {
        REPORT_PATH,
        RESULTS_PATH
    }
}
