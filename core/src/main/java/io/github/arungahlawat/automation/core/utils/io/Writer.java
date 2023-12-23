package io.github.arungahlawat.automation.core.utils.io;

public interface Writer<T> {
    String write(T object, String filePath);
}
