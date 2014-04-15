package com.beactive.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean checkEmail(String email) {
        /* It's assumed that the email is correct if it matches with regexp.
         * The regexp description:
         * www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression
         * Using this regexp gives more accurate results than using Patterns.EMAIL_ADDRESS.
         */
        final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (email.length() > 0 && !matcher.matches()) {
            return false;
        }
        return true;
    }
}
