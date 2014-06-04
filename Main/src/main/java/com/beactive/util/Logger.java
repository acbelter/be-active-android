package com.beactive.util;

import android.util.Log;

public class Logger {
    private static final boolean DEBUG = true;
    private static final String TAG = "beActive-debug";

    public static void log(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}
