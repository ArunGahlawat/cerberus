package io.github.arungahlawat.automation.core.utils.io.impl.writers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Writer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class DefaultWriter implements Writer<Object> {
    @Override
    public String write(Object object, String filePath) {
        return write(object, filePath, false);
    }

    public String write(Object data, String filePath, boolean append) {
        filePath = FileUtils.transformFilePath(filePath);
        try {
            org.apache.commons.io.FileUtils.createParentDirectories(new File(filePath));
            if (data instanceof byte[])
                org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(filePath), (byte[]) data, append);
            else
                org.apache.commons.io.FileUtils.writeStringToFile(new File(filePath), (String) data, Charset.defaultCharset(), append);
        } catch (IOException e) {
            Log.error("Exception in writing file {}", e.getMessage());
        }
        return new File(filePath).getAbsolutePath();
    }
}
