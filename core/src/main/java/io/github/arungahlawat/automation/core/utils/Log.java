package io.github.arungahlawat.automation.core.utils;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Log {
    private static Logger log;
    private static boolean isInitialised = false;

    private Log() {
    }

    private static void init() {
        if (isInitialised)
            return;
        init(Log.class.getName());
    }

    public static void init(String logName) {
        if (isInitialised)
            return;
        String logLevel = "false".equals(System.getProperty("logLevel", "false")) ? ("true".equals(System.getProperty("intellij.debug.agent", "INFO")) ? "DEBUG" : "INFO") : System.getProperty("logLevel");
        System.setProperty("org.slf4j.simpleLogger.log." + logName, logLevel);
        log = LoggerFactory.getLogger(logName);
        isInitialised = true;
    }

    public static String formatMessage(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

    public static boolean isInfoEnabled() {
        init();
        return log.isInfoEnabled();
    }

    public static void info(String msg) {
        init();
        log.info(msg);
        report(msg);
    }

    public static void info(String format, Object arg) {
        init();
        log.info(format, arg);
        report(format, arg);
    }

    public static void info(String format, Object arg1, Object arg2) {
        init();
        log.info(format, arg1, arg2);
        report(format, arg1, arg2);
    }

    public static void info(String format, Object... arguments) {
        init();
        log.info(format, arguments);
        report(format, arguments);
    }

    public static void info(String msg, Throwable t) {
        init();
        log.info(msg, t);
        report(msg, t);
    }

    public static boolean isInfoEnabled(Marker marker) {
        init();
        return log.isInfoEnabled(marker);
    }

    public static void info(Marker marker, String msg) {
        init();
        log.info(marker, msg);
        report(msg);
    }

    public static void info(Marker marker, String format, Object arg) {
        init();
        log.info(marker, format, arg);
        report(format, arg);
    }

    public static void info(Marker marker, String format, Object arg1, Object arg2) {
        init();
        log.info(marker, format, arg1, arg2);
        report(format, arg1, arg2);
    }

    public static void info(Marker marker, String format, Object... arguments) {
        init();
        log.info(marker, format, arguments);
        report(format, arguments);
    }

    public static void info(Marker marker, String msg, Throwable t) {
        init();
        log.info(marker, msg, t);
        report(msg, t);
    }

    public static boolean isWarnEnabled() {
        init();
        return log.isWarnEnabled();
    }

    public static void warn(String msg) {
        init();
        log.warn(msg);
        report("[WARN] {}", msg);
    }

    public static void warn(String format, Object arg) {
        init();
        log.warn(format, arg);
        report("[WARN] " + format, arg);
    }

    public static void warn(String format, Object... arguments) {
        init();
        log.warn(format, arguments);
        report("[WARN] " + format, arguments);
    }

    public static void warn(String format, Object arg1, Object arg2) {
        init();
        log.warn(format, arg1, arg2);
        report("[WARN] " + format, arg1, arg2);
    }

    public static void warn(String msg, Throwable t) {
        init();
        log.warn(msg, t);
        report("[WARN] " + msg, t);
    }

    public static boolean isWarnEnabled(Marker marker) {
        init();
        return log.isWarnEnabled(marker);
    }

    public static void warn(Marker marker, String msg) {
        init();
        log.warn(marker, msg);
        report("[WARN] {}", msg);
    }

    public static void warn(Marker marker, String format, Object arg) {
        init();
        log.warn(marker, format, arg);
        report("[WARN] " + format, arg);
    }

    public static void warn(Marker marker, String format, Object arg1, Object arg2) {
        init();
        log.warn(marker, format, arg1, arg2);
        report("[WARN] " + format, arg1, arg2);
    }

    public static void warn(Marker marker, String format, Object... arguments) {
        init();
        log.warn(marker, format, arguments);
        report("[WARN] " + format, arguments);
    }

    public static void warn(Marker marker, String msg, Throwable t) {
        init();
        log.warn(marker, msg, t);
        report("[WARN] " + msg, t);
    }

    public static boolean isErrorEnabled() {
        init();
        return log.isErrorEnabled();
    }

    public static void error(String msg) {
        init();
        log.error(msg);
        report("[ERROR] {}", msg);
    }

    public static void error(String format, Object arg) {
        init();
        log.error(format, arg);
        report("[ERROR] " + format, arg);
    }

    public static void error(String format, Object arg1, Object arg2) {
        init();
        log.error(format, arg1, arg2);
        report("[ERROR] " + format, arg1, arg2);
    }

    public static void error(String format, Object... arguments) {
        init();
        log.error(format, arguments);
        report("[ERROR] " + format, arguments);
    }

    public static void error(String msg, Throwable t) {
        init();
        log.error(msg, t);
        report("[ERROR] " + msg, t);
    }

    public static boolean isErrorEnabled(Marker marker) {
        init();
        return log.isErrorEnabled(marker);
    }

    public static void error(Marker marker, String msg) {
        init();
        log.error(marker, msg);
        report("[ERROR] {}", msg);
    }

    public static void error(Marker marker, String format, Object arg) {
        init();
        log.error(marker, format, arg);
        report("[ERROR] " + format, arg);
    }

    public static void error(Marker marker, String format, Object arg1, Object arg2) {
        init();
        log.error(marker, format, arg1, arg2);
        report("[ERROR] " + format, arg1, arg2);
    }

    public static void error(Marker marker, String format, Object... arguments) {
        init();
        log.error(marker, format, arguments);
        report("[ERROR] " + format, arguments);
    }

    public static void error(Marker marker, String msg, Throwable t) {
        init();
        log.error(marker, msg, t);
        report("[ERROR] " + msg, t);
    }

    public static String getName() {
        init();
        return log.getName();
    }

    public static boolean isDebugEnabled() {
        init();
        return log.isDebugEnabled();
    }

    public static void debug(String msg) {
        init();
        log.debug(msg);
    }

    public static void debug(String format, Object arg) {
        init();
        log.debug(format, arg);
    }

    public static void debug(String format, Object arg1, Object arg2) {
        init();
        log.debug(format, arg1, arg2);
    }

    public static void debug(String format, Object... arguments) {
        init();
        log.debug(format, arguments);
    }

    public static void debug(String msg, Throwable t) {
        init();
        log.debug(msg, t);
    }

    public static boolean isDebugEnabled(Marker marker) {
        init();
        return log.isDebugEnabled(marker);
    }

    public static void debug(Marker marker, String msg) {
        init();
        log.debug(marker, msg);
    }

    public static void debug(Marker marker, String format, Object arg) {
        init();
        log.debug(marker, format, arg);
    }

    public static void debug(Marker marker, String format, Object arg1, Object arg2) {
        init();
        log.debug(marker, format, arg1, arg2);
    }

    public static void debug(Marker marker, String format, Object... arguments) {
        init();
        log.debug(marker, format, arguments);
    }

    public static void debug(Marker marker, String msg, Throwable t) {
        init();
        log.debug(marker, msg, t);
    }

    public static boolean isTraceEnabled() {
        init();
        return log.isTraceEnabled();
    }

    public static void trace(String msg) {
        init();
        log.trace(msg);
    }

    public static void trace(String format, Object arg) {
        init();
        log.trace(format, arg);
    }

    public static void trace(String format, Object arg1, Object arg2) {
        init();
        log.trace(format, arg1, arg2);
    }

    public static void trace(String format, Object... arguments) {
        init();
        log.trace(format, arguments);
    }

    public static void trace(String msg, Throwable t) {
        init();
        log.trace(msg, t);
    }

    public static boolean isTraceEnabled(Marker marker) {
        init();
        return log.isTraceEnabled(marker);
    }

    public static void trace(Marker marker, String msg) {
        init();
        log.trace(marker, msg);
    }

    public static void trace(Marker marker, String format, Object arg) {
        init();
        log.trace(marker, format, arg);
    }

    public static void trace(Marker marker, String format, Object arg1, Object arg2) {
        init();
        log.trace(marker, format, arg1, arg2);
    }

    public static void trace(Marker marker, String format, Object... argArray) {
        init();
        log.trace(marker, format, argArray);
    }

    public static void trace(Marker marker, String msg, Throwable t) {
        init();
        log.trace(marker, msg, t);
    }

    @Step("{0}")
    public static void report(String msg) {
    }

    public static void report(String format, Object arg) {
        report(formatMessage(format, arg));
    }

    public static void report(String format, Object arg1, Object arg2) {
        report(formatMessage(format, arg1, arg2));
    }

    public static void report(String format, Object... arguments) {
        report(formatMessage(format, arguments));
    }

    public static void report(String msg, Throwable t) {
        report(formatMessage(msg + " Exception:{}", t.getMessage()));
    }




}
