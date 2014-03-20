package com.beactive.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    // FIXME Use another date format
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static String parseDate(long millis) {
        return DATE_FORMAT.format(new Date(millis));
    }

    public static String parseTime(long millis) {
        return TIME_FORMAT.format(new Date(millis));
    }
}
