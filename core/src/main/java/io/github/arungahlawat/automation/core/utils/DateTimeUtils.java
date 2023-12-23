package io.github.arungahlawat.automation.core.utils;

import io.github.arungahlawat.automation.core.Constants;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static LocalDate getCurrentDate() {
        return getCurrentDate(Constants.IST_ZONE);
    }

    /**
     * Returns LocalDate instance of provided zone
     *
     * @param zone zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return LocalDate
     */
    public static LocalDate getCurrentDate(String zone) {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).atZone(ZoneId.of(zone)).toLocalDate();
    }

    public static LocalTime getCurrentTime() {
        return getCurrentTime(Constants.IST_ZONE);
    }

    /**
     * Returns LocalTime instance of provided zone
     *
     * @param zone zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return LocalTime
     */
    public static LocalTime getCurrentTime(String zone) {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).atZone(ZoneId.of(zone)).toLocalTime();
    }

    public static LocalDateTime getCurrentDateTime() {
        return getCurrentDateTime(Constants.IST_ZONE);
    }

    /**
     * Returns LocalDateTime instance of provided zone
     *
     * @param zone zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return LocalDateTime
     */
    public static LocalDateTime getCurrentDateTime(String zone) {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).atZone(ZoneId.of(zone)).toLocalDateTime();
    }

    public static Long getCurrentEpoch() {
        return getCurrentEpoch(Constants.IST_ZONE);
    }

    /**
     * Returns current epoch seconds at provided zone
     *
     * @param zone zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return epoch seconds
     */
    public static Long getCurrentEpoch(String zone) {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).atZone(ZoneId.of(zone)).toEpochSecond();
    }

    public static Long getEpochSecondsOfDayStart() {
        return getEpochSecondsOfDayStart(Constants.IST_ZONE);
    }

    public static Long getEpochSecondsOfDayStart(String zone) {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).atZone(ZoneId.of(zone)).toLocalDate().atStartOfDay().toEpochSecond(ZoneOffset.of(zone));
    }

    public static Long getEpochSecondsOfDayStart(LocalDate localDate) {
        return getEpochSecondsOfDayStart(localDate, Constants.IST_ZONE);
    }

    public static Long getEpochSecondsOfDayStart(LocalDate localDate, String zone) {
        return localDate.atStartOfDay().toEpochSecond(ZoneOffset.of(zone));
    }

    public static Long getEpochSecondsOfDayStart(LocalDateTime localDateTime) {
        return getEpochSecondsOfDayStart(localDateTime, Constants.IST_ZONE);
    }

    public static Long getEpochSecondsOfDayStart(LocalDateTime localDateTime, String zone) {
        return getEpochSecondsOfDayStart(localDateTime.toLocalDate(), zone);
    }

    public static Long getEpochSecondsOfDayEnd() {
        return getEpochSecondsOfDayEnd(Constants.IST_ZONE);
    }

    public static Long getEpochSecondsOfDayEnd(String zone) {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).atZone(ZoneId.of(zone)).toLocalDate().atStartOfDay().plusDays(1).minusNanos(1000).toEpochSecond(ZoneOffset.of(zone));
    }

    public static Long getEpochSecondsOfDayEnd(LocalDate localDate) {
        return getEpochSecondsOfDayEnd(localDate, Constants.IST_ZONE);
    }

    public static Long getEpochSecondsOfDayEnd(LocalDate localDate, String zone) {
        return localDate.atStartOfDay().plusDays(1).minusNanos(1000).toEpochSecond(ZoneOffset.of(zone));
    }

    public static Long getEpochSecondsOfDayEnd(LocalDateTime localDateTime) {
        return getEpochSecondsOfDayEnd(localDateTime, Constants.IST_ZONE);
    }

    public static Long getEpochSecondsOfDayEnd(LocalDateTime localDateTime, String zone) {
        return getEpochSecondsOfDayEnd(localDateTime.toLocalDate(), zone);
    }

    public static Long getEpochSeconds(LocalDateTime localDateTime) {
        return getEpochSeconds(localDateTime, Constants.IST_ZONE);
    }

    public static Long getEpochSeconds(LocalDateTime localDateTime, String zone) {
        return localDateTime.toEpochSecond(ZoneOffset.of(zone));
    }

    public static Long getEpochMillis(LocalDateTime localDateTime) {
        return getEpochMillis(localDateTime, Constants.IST_ZONE);
    }

    public static Long getEpochMillis(LocalDateTime localDateTime, String zone) {
        return Math.multiplyExact(getEpochSeconds(localDateTime, zone), 1000);
    }

    public static Long getEpochMillisOfDayStart() {
        return getEpochMillisOfDayStart(Constants.IST_ZONE);
    }

    public static Long getEpochMillisOfDayStart(String zone) {
        return Math.multiplyExact(getEpochSecondsOfDayStart(zone), 1000);
    }

    public static Long getEpochMillisOfDayStart(LocalDate localDate) {
        return getEpochMillisOfDayStart(localDate, Constants.IST_ZONE);
    }

    public static Long getEpochMillisOfDayStart(LocalDate localDate, String zone) {
        return Math.multiplyExact(getEpochSecondsOfDayStart(localDate, zone), 1000);
    }

    public static Long getEpochMillisOfDayStart(LocalDateTime localDateTime) {
        return getEpochMillisOfDayStart(localDateTime, Constants.IST_ZONE);
    }

    public static Long getEpochMillisOfDayStart(LocalDateTime localDateTime, String zone) {
        return Math.multiplyExact(getEpochSecondsOfDayStart(localDateTime, zone), 1000);
    }

    public static Long getEpochMillisOfDayEnd() {
        return getEpochMillisOfDayEnd(Constants.IST_ZONE);
    }

    public static Long getEpochMillisOfDayEnd(String zone) {
        return Math.multiplyExact(getEpochSecondsOfDayEnd(zone), 1000);
    }

    public static Long getEpochMillisOfDayEnd(LocalDate localDate) {
        return getEpochMillisOfDayEnd(localDate, Constants.IST_ZONE);
    }

    public static Long getEpochMillisOfDayEnd(LocalDate localDate, String zone) {
        return Math.multiplyExact(getEpochSecondsOfDayEnd(localDate, zone), 1000);
    }

    public static Long getEpochMillisOfDayEnd(LocalDateTime localDateTime) {
        return getEpochMillisOfDayEnd(localDateTime, Constants.IST_ZONE);
    }

    public static Long getEpochMillisOfDayEnd(LocalDateTime localDateTime, String zone) {
        return Math.multiplyExact(getEpochSecondsOfDayEnd(localDateTime, zone), 1000);
    }

    public static Long getCurrentEpochMillis() {
        return getCurrentEpochMillis(Constants.IST_ZONE);
    }

    /**
     * Returns current epoch in milliseconds
     *
     * @param zone zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return epoch milliseconds
     */
    public static Long getCurrentEpochMillis(String zone) {
        return Math.multiplyExact(getCurrentEpoch(zone), 1000);
    }

    /**
     * Convert date time of given pattern to LocalDateTime
     *
     * @param dateTime date time in string
     * @param pattern  pattern of dateTime eg. yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     */
    public static LocalDateTime convertDateTime(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Convert date of given pattern to LocalDate
     *
     * @param date    date in string
     * @param pattern pattern of date eg. yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate convertDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Convert time of given pattern to LocalTime
     *
     * @param time    time in string
     * @param pattern pattern of time eg. HH:mm:ss
     * @return LocalTime
     */
    public static LocalTime convertTime(String time, String pattern) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime convertEpochMillis(long epochMillis) {
        return convertEpochMillis(epochMillis, Constants.IST_ZONE);
    }

    /**
     * Convert epoch millis to LocalDateTime
     *
     * @param epochMillis epoch millis
     * @param zone        zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return LocalDateTime
     */
    public static LocalDateTime convertEpochMillis(long epochMillis, String zone) {
        return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.of(zone)).toLocalDateTime();
    }

    public static LocalDateTime convertEpoch(long epochSeconds) {
        return convertEpoch(epochSeconds, Constants.IST_ZONE);
    }

    /**
     * Convert epoch seconds to LocalDateTime
     *
     * @param epochSeconds epoch seconds
     * @param zone         zone name like Asia/Kolkata | +05:30 | GMT+05:30
     * @return LocalDateTime
     */
    public static LocalDateTime convertEpoch(long epochSeconds, String zone) {
        return convertEpochMillis(Math.multiplyExact(epochSeconds, 1000), zone);
    }

    public static String formatCurrentDate(String pattern) {
        return format(getCurrentDateTime(), pattern);
    }

    public static String formatCurrentDateInUtc(String pattern) {
        return format(getCurrentDateTime(Constants.UTC_ZONE), pattern);
    }

    /**
     * format LocalDateTime object to desired string pattern
     *
     * @param dateTime LocalDateTime instance
     * @param pattern  desired pattern yyyy-MM-dd HH:mm:ss
     * @return formatted string
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
