package io.github.arungahlawat.automation.core.utils.io.impl.readers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Reader;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

public class PropertiesReader implements Reader<Configuration> {
    public static PropertiesReader instance;

    private PropertiesReader() {
    }

    public static PropertiesReader getInstance() {
        if (instance == null)
            instance = new PropertiesReader();
        return instance;
    }

    @Override
    public Configuration read(String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        if (!StringUtils.endsWithIgnoreCase(filePath, ".properties"))
            filePath += ".properties";
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName(filePath));
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException e) {
            Log.error("Error loading properties file {}. Error:{}", filePath, e.getMessage());
        }
        return null;
    }
}
