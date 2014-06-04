package com.beactive.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {
    // FIXME DON'T USE THIS!
    public static final int TIME_ZONE_OFFSET = 3; // Moscow TimeZone
    // FIXME Use another date format
    private static final DateFormat DATE_FORMAT;
    private static final DateFormat TIME_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
        DATE_FORMAT.setTimeZone(getTimeZone());
        TIME_FORMAT = new SimpleDateFormat("HH:mm");
        TIME_FORMAT.setTimeZone(getTimeZone());
    }

    public static String parseDate(long millis) {
        return DATE_FORMAT.format(new Date(millis));
    }

    public static String parseTime(long millis) {
        return TIME_FORMAT.format(new Date(millis));
    }

    private static TimeZone getTimeZone() {
        if (TIME_ZONE_OFFSET > 0) {
            return TimeZone.getTimeZone("GMT+" + TIME_ZONE_OFFSET);
        }
        if (TIME_ZONE_OFFSET < 0) {
            return TimeZone.getTimeZone("GMT-" + Math.abs(TIME_ZONE_OFFSET));
        }
        return TimeZone.getTimeZone("GMT+0");
    }
}
