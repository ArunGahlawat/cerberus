package io.github.arungahlawat.automation.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils extends RandomStringUtils {
    public static String getHash(Object object) {
        return object == null ? null : String.valueOf(object.hashCode());
    }

    public static UUID getUUID(String uuidReferenceString) {
        return UUID.fromString(uuidReferenceString);
    }

    public static UUID getUUID() {
        return UUID.randomUUID();
    }

    public static Integer getInteger(Integer min, Integer max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static Double getDouble(Double min, Double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static Long getLong(Long min, Long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
}
