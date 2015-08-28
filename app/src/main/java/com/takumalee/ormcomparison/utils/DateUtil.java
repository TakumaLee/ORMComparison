package com.takumalee.ormcomparison.utils;

import android.content.SharedPreferences;
import android.util.Log;


import com.takumalee.ormcomparison.context.ApplicationContextSingleton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A Utility class containing the methods for date manipulation
 *
 * @author TakumaLee
 */
public class DateUtil {
    private static final String TAG = DateUtil.class.getSimpleName();
    /**
     * The reference date of the iOS system.
     */
    public static final long NS_DATE_REFERENCE_SEC = 978307200;
    /**
     * The default formatter for short date without year
     */
    private static final DateFormat SHORT_DATE_WITHOUT_YEAR_FORMATTER = new SimpleDateFormat("dd/MM");
    /**
     * The default formatter for short date
     */
    private static final DateFormat SHORT_DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * The default formatter for full date
     */
    private static final DateFormat FULL_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * The default formatter for get milliseconds
     */
    private static final DateFormat FULL_DATE_FORMATTER_MILLISECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * The default formatter for Broadcast
     */
    private static final DateFormat BROADCAST_FULL_DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * The default formatter for Transaction
     */
    private static final DateFormat TRANSACTION_FULL_DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    /**
     * The formatter for parse date data from web service
     */
    private static final DateFormat ISO8601_DATE_FORMATTER_TYPEI = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
    /**
     * The formatter for parse date data from web service without zone
     */
    private static final DateFormat ISO8601_DATE_FORMATTER_TYPEI_WITHOUT_ZONE = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    /**
     * The formatter for ISO8601
     */
    private static final DateFormat ISO8601_DATE_FORMATTER_TYPEII = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
    private static final DateFormat ISO8601_DATE_FORMATTER_TYPEII_WITHOUT_ZONE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    /**
     * The formatter for full text date format
     */
    private static final DateFormat DATE_PATTERN_FULL_TEXT_FORMATTER = new SimpleDateFormat("EEEEE dd MMMMM");
    /**
     * The formatter for full text datetime format
     */
    private static final DateFormat DATETIME_PATTERN_FULL_TEXT_FORMATTER = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    /**
     * The formatter for time only
     */
    private static final DateFormat TIME_PATTERN_SHORT_FORMATTER = new SimpleDateFormat("HH:mm", Locale.US);
    /**
     * Separator for time range formatter
     */
    private static final String TIME_RANGE_SEPARATOR = " - ";

    static {
        TimeZone timezone = TimeZone.getTimeZone("GMT+0800");
        BROADCAST_FULL_DATE_FORMATTER.setTimeZone(timezone);
        SHORT_DATE_WITHOUT_YEAR_FORMATTER.setTimeZone(timezone);
        SHORT_DATE_FORMATTER.setTimeZone(timezone);
        FULL_DATE_FORMATTER.setTimeZone(timezone);
        ISO8601_DATE_FORMATTER_TYPEI.setTimeZone(timezone);
        ISO8601_DATE_FORMATTER_TYPEI_WITHOUT_ZONE.setTimeZone(timezone);
        ISO8601_DATE_FORMATTER_TYPEII.setTimeZone(timezone);
        TIME_PATTERN_SHORT_FORMATTER.setTimeZone(timezone);
        DATE_PATTERN_FULL_TEXT_FORMATTER.setTimeZone(timezone);
        DATETIME_PATTERN_FULL_TEXT_FORMATTER.setTimeZone(timezone);
    }

    /**
     * Private Constructor to avoid initialization
     */
    private DateUtil() {
    }

    /**
     * 取得當前日期所在週的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得當前日期所在週的第一天
     *
     * @param calendar
     * @return
     */
    public static Calendar getFirstDayOfWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        return calendar;
    }

    /**
     * 取得當前日期所在週的最後一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    /**
     * 取得當前日期所在週的最後一天
     *
     * @param calendar
     * @return
     */
    public static Calendar getLastDayOfWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6);
        return calendar;
    }

    /**
     * 返回指定日期的月的第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月的第一天
     *
     * @return
     */
    public static Calendar getFirstDayOfMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        return calendar;
    }

    /**
     * 返回指定日期的月的最後一天
     *
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月的最後一天
     *
     * @return
     */
    public static Calendar getLastDayOfMonth(Calendar calendar) {
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    /**
     * 獲取某年第一天日期
     *
     * @param date 年份
     * @return Date
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 獲取某年第一天日期
     *
     * @param calendar 年份
     * @return Date
     */
    public static Calendar getFirstDayOfYear(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar;
    }

    /**
     * 返回指定日期的年的最後一天
     *
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.YEAR), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的年的最後一天
     *
     * @return
     */
    public static Calendar getLastDayOfYear(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), 11, 31);
        return calendar;
    }

    /**
     * Converts the timestamp from iOS system to the Date object in Java
     *
     * @param timestamp the timestamp from iOS
     * @return a Date object represents the given timestamp from iOS System
     */
    public static Date getDateFromNSTimestamp(long timestamp) {
        Log.v(TAG, "getDateFromNSTimestamp() : timestamp = " + timestamp);
        long time = (NS_DATE_REFERENCE_SEC + timestamp) * 1000;
        return new Date(time);
    }

    /**
     * Gets the current timestamp in the iOS system.
     */
    public static long getCurrentNSTimestamp() {
        Log.v(TAG, "getCurrentNSTimestamp()");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        long time = c.getTime().getTime();
        return (time - (NS_DATE_REFERENCE_SEC * 1000)) / 1000;
    }

    /**
     * Converts the Java timestamp to the iOS timestamp and create a Date object
     * from the iOS timestamp.
     */
    public static Date getDateFromTimestamp(long timestamp) {
        Log.v(TAG, "getDateFromTimestamp() timestamp = " + timestamp);
        long time = (timestamp - (NS_DATE_REFERENCE_SEC * 1000)) / 1000;
        return new Date(time);
    }

    /**
     * Formats the given date using the default short date formatter.
     */
    public static String formatShortDateWithoutYear(Date date) {
        Log.v(TAG, "formatShortDate() date = " + date);
        String dateString = "";
        if (date != null) {
            dateString = SHORT_DATE_WITHOUT_YEAR_FORMATTER.format(date);
        }
        return dateString;
    }

    /**
     * Formats the given date using the default short date formatter.
     */
    public static String formatShortDate(Date date) {
        Log.v(TAG, "formatShortDate() date before = " + date);
        String dateString = "";
        if (date != null) {
            dateString = SHORT_DATE_FORMATTER.format(date);
        }
        return dateString;
    }

    /**
     * Formats the given date using the default full date formatter.
     */
    public static String formatFullDate(Date date) {
        Log.v(TAG, "formatFullDate() date = " + date);
        String dateString = "";
        if (date != null) {
            dateString = FULL_DATE_FORMATTER.format(date);
        }
        return dateString;
    }

    /**
     * Formats the given date using the default Broadcast.
     */
    public static String formatFullDateForBroadcast(Date date) {
        Log.v(TAG, "formatFullDateForBroadcast() date = " + date);
        String dateString = "";
        if (date != null) {
            dateString = BROADCAST_FULL_DATE_FORMATTER.format(date);
        }
        return dateString;
    }

    /**
     * Formats the given date using the default transaction.
     */
    public static String formatFullDateForTransaction(Date date) {
        Log.v(TAG, "formatFullDateForTransaction() date = " + date);
        String dateString = "";
        if (date != null) {
            dateString = TRANSACTION_FULL_DATE_FORMATTER.format(date);
        }
        return dateString;
    }

    /**
     * Parse string to date
     *
     * @param date
     * @throws ParseException
     */
    public static Date parseISO8601Date(String date) throws ParseException {
        Log.v(TAG, "parseISO8601Date() date=" + date);
        Date returnDate = null;
        date.replaceAll("\\+0([0-9]){1}\\:00", "+0$100");
        date.replaceAll(":", "");
        //Without Zone: To avoid time difference
        returnDate = ISO8601_DATE_FORMATTER_TYPEI_WITHOUT_ZONE.parse(date);
        return returnDate;
    }

    /**
     * Parse string to date without zone
     *
     * @param date
     * @throws ParseException
     */
    public static Date parseISO8601DateWithoutZone(String date) throws ParseException {
        Log.v(TAG, "parseISO8601DateWithoutZone() date=" + date);
        Date returnDate = null;
        date.replaceAll("\\+0([0-9]){1}\\:00", "+0$100");
        date.replaceAll(":", "");
        returnDate = ISO8601_DATE_FORMATTER_TYPEI_WITHOUT_ZONE.parse(date);
        return returnDate;
    }

    /**
     * Converts the Date object to the date string in the ISO 8601 format
     */
    public static final String getISO8601DateString(Date date) {
        Log.v(TAG, "getDateIdo8601() date = " + date);
        return ISO8601_DATE_FORMATTER_TYPEII.format(date);
    }

    public static final String getISO8601DateWithoutZoneString(Date date) {
        Log.v(TAG, "getISO8601DateWithoutZoneString() date = " + date);
        return ISO8601_DATE_FORMATTER_TYPEII_WITHOUT_ZONE.format(date);
    }

    /**
     * Converts the Date object to the date string in the full text format
     */
    public static final String getFullTextDateString(Date date) {
        Log.v(TAG, "getFullTextDateString() date = " + date);
        return DATE_PATTERN_FULL_TEXT_FORMATTER.format(date);
    }

    /**
     * Converts the Date object to the datetime string in the full text format
     */
    public static final String getFullTextDateTimeString(Date date) {
        Log.v(TAG, "getFullTextDateTimeString() date = " + date);
        return DATETIME_PATTERN_FULL_TEXT_FORMATTER.format(date);
    }

    /**
     * Converts the Date object to the time string in the short format
     */
    public static final String getShortTimeString(Date date) {
        Log.v(TAG, "getShortTimeString() date = " + date);
        return TIME_PATTERN_SHORT_FORMATTER.format(date);
    }

    /**
     * Converts the 2 Date objects to the time string in the short range format
     */
    public static final String getShortTimeRangeString(Date start, Date end) {
        Log.v(TAG, "getShortTimeRangeString() start = " + start + ", end=" + end);
        return new StringBuilder(TIME_PATTERN_SHORT_FORMATTER.format(start)).append(TIME_RANGE_SEPARATOR)
                .append(TIME_PATTERN_SHORT_FORMATTER.format(end)).toString();
    }

    public static boolean isUpdateRequired(Date lastUpdate, long now, long cacheMaxTime) {
        if (lastUpdate == null) {
            return true;
        }

        return now - lastUpdate.getTime() > cacheMaxTime;
    }

    public static boolean isUpdateRequired(long lastUpdateTime, long now, long cacheMaxTime) {
        return now - lastUpdateTime > cacheMaxTime;
    }

    public static boolean isUpdateRequiredMillis(Date lastUpdated, long cacheMaxtime) {
        return isUpdateRequired(lastUpdated, Calendar.getInstance().getTimeInMillis(), cacheMaxtime);
    }

    public static boolean isUpdateRequiredMillis(long lastUpdatedTime, long cacheMaxtime) {
        return isUpdateRequired(lastUpdatedTime, Calendar.getInstance().getTimeInMillis(), cacheMaxtime);
    }

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    public static long getUnixTime() {
        return Calendar.getInstance().getTime().getTime();
    }

    public static void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static boolean checkAndUpdateForKeyAndFileWithCache(String key, String file, long cacheMaxTime) {

        SharedPreferences settings = ApplicationContextSingleton.getApplicationContext().getSharedPreferences(file, 0);
        Long lastRefresh = settings.getLong(key, 0);
        if (DateUtil.isUpdateRequired(lastRefresh, new Date().getTime(), cacheMaxTime)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong(key, new Date().getTime());
            editor.commit();
            return true;
        }
        return false;
    }

    public static void flushTimeForKeyAndFile(String key, String file) {
        SharedPreferences settings = ApplicationContextSingleton.getApplicationContext().getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void flushTimeForPrefFile(String file) {
        SharedPreferences settings = ApplicationContextSingleton.getApplicationContext().getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static int getAgeFromDate(long birthDate) {
        long msYear = 1000L * 60 * 60 * 24 * 365;
        int years = (int) ((DateUtil.getUnixTime() - birthDate) / msYear);
        return years;
    }

    /**
     * Date type for Chocolabs
     *
     * @author takumalee
     */

    public static long getFormatDate(String dateStr) {
        Date date = null;
        try {
            date = FULL_DATE_FORMATTER_MILLISECOND.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.v(TAG, dateStr);
        long timestamp = 0;
        if (date != null)
            timestamp = date.getTime();
        return timestamp;
    }

    public static String getFormateDateWithTimestamp(long timestamp) {
        String dateString;
        Date date = new Date(timestamp);
        dateString = FULL_DATE_FORMATTER.format(date);
        return dateString;
    }
}
