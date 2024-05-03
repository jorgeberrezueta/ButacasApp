package me.jorgeb.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public static Date parseDate(String date) {
        try {
            return DATE_FORMATTER.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Time parseTime(String time) {
        try {
            return Time.valueOf(time);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDate(Date date) {
        System.out.println("formatDate: " + DATE_FORMATTER.format(date));
        return DATE_FORMATTER.format(date);
    }

    public static String formatTime(Time time) {
        return time.toString();
    }

}
