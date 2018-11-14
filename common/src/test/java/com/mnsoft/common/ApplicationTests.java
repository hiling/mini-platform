package com.mnsoft.common;

import com.mnsoft.common.utils.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/8/2018.
 */
@RunWith(SpringRunner.class)
public class ApplicationTests {

    @Test
    public void contextLoads() {

        System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_LOCAL_DATE));
        //System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("LocalDate ISO_DATE:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_DATE));
        //System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_DATE_TIME));
        //System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_INSTANT));
        System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_WEEK_DATE));
        //System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_TIME));
        //System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDate(new Date()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        System.out.println("========================================");
        System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("BASIC_ISO_DATE:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println("ISO_LOCAL_DATE:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("ISO_LOCAL_DATE_TIME:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("ISO_DATE:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_DATE));
        System.out.println("ISO_DATE_TIME:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_DATE_TIME));
        //System.out.println("LocalDate:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_INSTANT));
        System.out.println("ISO_WEEK_DATE:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_WEEK_DATE));
        System.out.println("ISO_TIME:" + DateTimeUtils.dateToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_TIME));

        System.out.println("localDateToDate:" +DateTimeUtils.localDateToDate(LocalDate.now()).toString());

        LocalDateTime start = LocalDateTime.of(1993, 10, 13, 11, 11);
        LocalDateTime end = LocalDateTime.of(1994, 11, 13, 13, 13);
        System.out.println("年:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.YEARS));
        System.out.println("月:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.MONTHS));
        System.out.println("日:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.DAYS));
        System.out.println("半日:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.HALF_DAYS));
        System.out.println("小时:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.HOURS));
        System.out.println("分钟:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.MINUTES));
        System.out.println("秒:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.SECONDS));
        System.out.println("毫秒:" + DateTimeUtils.betweenTwoTime(start, end, ChronoUnit.MILLIS));

    }


}
