package com.beactive.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static String parseTime(long millis) {
        return TIME_FORMAT.format(new Date(millis));
    }
}
