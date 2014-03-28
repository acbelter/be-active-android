package com.beactive.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    private static final String TAG = "beActive";

    public static String readToString(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        BufferedReader inReader = null;
        String line;
        try {
            inReader = new BufferedReader(new InputStreamReader(is));
            while ((line = inReader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            if (inReader != null) {
                try {
                    inReader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Method readToString() can't close BufferedReader.");
                }
            }
        }

        return builder.toString();
    }
}
