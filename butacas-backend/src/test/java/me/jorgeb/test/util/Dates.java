package me.jorgeb.test.util;

import me.jorgeb.util.DateParser;

import java.util.Date;

public class Dates {



    public static Date yesterday() {
        return new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    }

    public static Date today() {
        return new Date(System.currentTimeMillis());
    }

    public static Date tomorrow() {
        return new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    }

    public static Date threeDaysAgo() {
        return new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000);
    }

    public static Date threeDaysLater() {
        return new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000);
    }

    public static Date fromString(String date) {
        return DateParser.parseDate(date);
    }

}
