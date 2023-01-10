package com.vaii_semestralka.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeConverter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter SCREEN_OUTPUT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static LocalDateTime parseDateTime(String date) {
        return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
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
}
