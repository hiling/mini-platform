package com.github.hiling.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/8/2018.
 */
public class DateTimeUtils extends DateUtils {

    /**
     * 将 Date 转换成LocalDate	 * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将 Date 转换成LocalDateTime	 * atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将LocalDate 转换成 Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将LocalDateTime 转换成 Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将localDate按照一定的格式转换成String
     * @param localDate
     * @param pattern 对LocalDate进行格式化时不能带时分秒信息，否则报错
     * @return
     */
    public static String localDateFormat(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将localDateTime 按照一定的格式转换成String
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将String 按照pattern 转换成LocalDate
     */
    public static LocalDate localDateParse(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将String 按照pattern 转换成LocalDateTime
     */
    public static LocalDateTime localDateTimeParse(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将date转换成String
     */
    public static String dateFormat(Date date, String pattern) {
        return localDateTimeFormat(dateToLocalDateTime(date), pattern);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field  单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }
}
