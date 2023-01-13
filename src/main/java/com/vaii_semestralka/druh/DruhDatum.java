package com.vaii_semestralka.druh;

import com.vaii_semestralka.converter.DateTimeConverter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DruhDatum {
    public static String getDateFromDruhDatum(String druh) {
        Calendar date = Calendar.getInstance();
        switch (druh) {
            case "Po 5min":
                date.add(Calendar.MINUTE, 5);
                break;
            case "Po 10min":
                date.add(Calendar.MINUTE, 10);
                break;
            case "Po 15min":
                date.add(Calendar.MINUTE, 15);
                break;
            case "Po 30min":
                date.add(Calendar.MINUTE, 30);
                break;
            case "Po 45min":
                date.add(Calendar.MINUTE, 45);
                break;
            case "Po 1hod":
                date.add(Calendar.HOUR, 1);
                break;
            case "Po 2hod":
                date.add(Calendar.HOUR, 2);
                break;
            case "Po 3hod":
                date.add(Calendar.HOUR, 3);
                break;
            case "Po 4hod":
                date.add(Calendar.HOUR, 4);
                break;
            case "Po 5hod":
                date.add(Calendar.HOUR, 5);
                break;
            case "Po 6hod":
                date.add(Calendar.HOUR, 6);
                break;
            case "Po 1 dni":
                date.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case "Po 2 dňoch":
                date.add(Calendar.DAY_OF_MONTH, 12);
                break;
            case "Po 3 dňoch":
                date.add(Calendar.DAY_OF_MONTH, 3);
                break;
            case "Po 4 dňoch":
                date.add(Calendar.DAY_OF_MONTH, 4);
                break;
            case "Po týždni":
                date.add(Calendar.WEEK_OF_MONTH, 1);
                break;
            case "Po mesiaci":
                date.add(Calendar.MONTH, 1);
                break;
        }

        return DateTimeConverter.getDateTimeStringFromDate(date);
    }
}
