package com.vaii_semestralka.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeConverter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMATER_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter SCREEN_OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final SimpleDateFormat SCREEN_OUTPUT_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    public static LocalDateTime parseDateTime(String date) {
        return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
    }
    public static LocalDateTime parseDateTimeT(String date) {
        return LocalDateTime.parse(date, DATE_TIME_FORMATTER_T);
    }
    public static String formatDateTime(LocalDateTime date) {
        return date.format(DATE_TIME_FORMATTER);
    }
    public static Date parseDate(String date) {
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException exception) {
            return null;
        }
    }

    public static String formatDate(Date date) {
        return DATE_FORMATTER.format(date);
    }

    public static String getScreenFormat(LocalDateTime localDateTime) {
        return localDateTime.format(SCREEN_OUTPUT_DATE_TIME_FORMATTER);
    }
    public static String getDateTimeStringFromDate(Calendar calendar) {
        return DATE_FORMATER_SECONDS.format(calendar.getTime());
    }
    public static String getDateScreenFormat(Date date) {
        return SCREEN_OUTPUT_DATE_FORMATTER.format(date);
    }
}
