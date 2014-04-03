package com.beactive.util;

import android.util.Log;

public class Logger {
    private static final boolean DEBUG = true;

    public static void log(String message) {
        if (DEBUG) {
            Log.d("DEBUG", message);
        }
    }
}
