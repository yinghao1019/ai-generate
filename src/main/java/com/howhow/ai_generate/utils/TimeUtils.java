package com.howhow.ai_generate.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TimeUtils {
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat sqlDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String TAIWAN_ZONE_OFFSET_ID = "+08:00";
    public static final ZoneOffset TAIWAN_ZONE_OFFSET = ZoneOffset.of(TAIWAN_ZONE_OFFSET_ID);
    public static final int TAIWAN_DATE_CUT = 1911;
    public static final int HOUR_OF_DAY = 24;
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final String EXCEL_DATE_FORMAT = "yyyyMMdd";

    private TimeUtils() {}

    public static Long getCurrentUTCMilliseconds() {
        return toUTCMilliseconds(getCurrentUTCDateTime());
    }

    public static OffsetDateTime getCurrentUTCDateTime() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    public static OffsetDateTime getCurrentDateTime(Integer hours) {
        return getCurrentUTCDateTime().plusHours(hours);
    }

    public static OffsetDateTime toOffsetDateTime(Long millis) {
        return Optional.ofNullable(millis)
                .map(utcMillis -> Instant.ofEpochMilli(utcMillis).atOffset(ZoneOffset.UTC))
                .orElse(null);
    }

    public static Long toUTCMilliseconds(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        } else {
            return offsetDateTime.toInstant().atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
        }
    }

    public static Date toDate(String dateString) {
        Date date = null;
        try {
            if (StringUtils.isNotEmpty(dateString)) {
                date = dateFormatter.parse(dateString);
            }
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static OffsetDateTime toOffsetDateTime(String dateString) {
        OffsetDateTime dateTime = null;
        try {
            if (StringUtils.isNotEmpty(dateString)) {
                dateTime = sqlDateFormatter.parse(dateString).toInstant().atOffset(ZoneOffset.UTC);
            }
            return dateTime;
        } catch (ParseException e) {
            return null;
        }
    }

    public static OffsetDateTime toOffsetDateTime(Timestamp timestamp) {
        OffsetDateTime offsetDateTime = null;
        if (timestamp != null) {
            offsetDateTime =
                    OffsetDateTime.ofInstant(
                            Instant.ofEpochMilli(timestamp.getTime()), ZoneId.of("UTC"));
        }
        return offsetDateTime;
    }

    public static Date toDate(OffsetDateTime offsetDateTime) {
        Date date = null;
        if (offsetDateTime != null) {
            date = Date.from(offsetDateTime.toInstant());
        }
        return date;
    }

    public static boolean isValidDateString(String str) {
        try {
            dateFormatter.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isOnTime(int timezone, int time) {
        int hour = getCurrentUTCDateTime().getHour() + timezone;
        if (hour >= HOUR_OF_DAY) {
            hour -= HOUR_OF_DAY;
        }
        return hour == time;
    }

    public static OffsetDateTime getTaiwanNow() {
        return OffsetDateTime.now().minusYears(TAIWAN_DATE_CUT);
    }

    public static String toString(Long millis) {
        return toString(toOffsetDateTime(millis));
    }

    public static String toString(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            String dateStr = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(offsetDateTime);
            return dateStr;
        }
        return null;
    }

    public static String toExcelString(OffsetDateTime offsetDateTime) {
        String dateStr = DateTimeFormatter.ofPattern(EXCEL_DATE_FORMAT).format(offsetDateTime);
        return dateStr;
    }

    public static boolean beforeNow(OffsetDateTime offsetDateTime) {
        return getCurrentUTCDateTime().isAfter(offsetDateTime);
    }

    public static boolean isEqualOrBefore(OffsetDateTime offsetDateTime, OffsetDateTime other) {
        return offsetDateTime.isEqual(other) || offsetDateTime.isBefore(other);
    }

    public static boolean isEqualOrAfter(OffsetDateTime offsetDateTime, OffsetDateTime other) {
        return offsetDateTime.isEqual(other) || offsetDateTime.isAfter(other);
    }

    public static boolean isBetween(
            OffsetDateTime start, OffsetDateTime end, OffsetDateTime dateTime) {
        return isEqualOrBefore(start, dateTime) && isEqualOrAfter(end, dateTime);
    }

    public static boolean isDateEqual(OffsetDateTime date, OffsetDateTime other) {
        return (date.getYear() == other.getYear())
                && (date.getMonthValue() == other.getMonthValue())
                && (date.getDayOfMonth() == other.getDayOfMonth());
    }

    public static boolean isNowBetween(OffsetDateTime startDate, OffsetDateTime endDate) {
        OffsetDateTime now = OffsetDateTime.now();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    public static boolean notEqual(OffsetDateTime date, Long other) {
        return !date.isEqual(toOffsetDateTime(other));
    }

    public static OffsetDateTime getStartOfDay(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            return offsetDateTime.with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());
        } else {
            return null;
        }
    }

    public static long getDiffDays(OffsetDateTime startDate, OffsetDateTime endDate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return days > 0 ? days : 0;
    }

    public static OffsetDateTime getEndOfDay(Long millis) {
        return getEndOfDay(toOffsetDateTime(millis));
    }

    public static OffsetDateTime getEndOfDay(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            return offsetDateTime.withHour(23).withMinute(59).withSecond(59);
        } else {
            return null;
        }
    }

    public static boolean isFirstDayOfMonth(Integer tz) {
        return getCurrentUTCDateTime().plusHours(tz).getDayOfMonth() == FIRST_DAY_OF_MONTH;
    }

    public static OffsetDateTime getNextMonthDateTime(Integer tz) {
        return getCurrentUTCDateTime().plusHours(tz).plusMonths(1);
    }

    public static List<Integer> getYearsBetweenDate(
            OffsetDateTime startDate, OffsetDateTime endDate) {
        return getYearsBetweenDate(startDate.getYear(), endDate.getYear());
    }

    public static List<Integer> getYearsBetweenDate(Integer startYear, Integer endYear) {
        List<Integer> years = new ArrayList<>();
        if (startYear != null && endYear != null) {
            for (Integer year = startYear; year <= endYear; year++) {
                years.add(year);
            }
        } else {
            years.add(OffsetDateTime.now().getYear());
        }
        return years;
    }

    public static OffsetDateTime getFirstDayOfYear(Integer year) {
        return OffsetDateTime.of(year, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
    }

    public static OffsetDateTime getEndDayOfYear(Integer year) {
        return OffsetDateTime.of(year, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC);
    }
}
