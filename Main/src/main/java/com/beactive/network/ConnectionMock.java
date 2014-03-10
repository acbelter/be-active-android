package com.beactive.network;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.beactive.R;
import com.beactive.adapter.BaseScheduleItem;
import com.beactive.adapter.EventItem;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ConnectionMock implements ServerConnection {
    private static final String TAG = "beActive";
    private Resources mResources;

    public ConnectionMock(Context context) {
        mResources = context.getResources();
    }

    @Override
    public List<BaseScheduleItem> getSchedule() {
        try {
            String jsonStr = readToString(mResources.openRawResource(R.raw.schedule));
            return DataParser.parseScheduleFromJson(jsonStr);
        } catch (IOException e) {
            Log.e(TAG, "Can't open schedule from resources.");
        } catch (JSONException e) {
            Log.e(TAG, "Can't parse schedule from resources.");
        }
        return null;
    }

    @Override
    public List<EventItem> getEvents() {
        try {
            String jsonStr = readToString(mResources.openRawResource(R.raw.events));
            return DataParser.parseEventsFromJson(jsonStr);
        } catch (IOException e) {
            Log.e(TAG, "Can't open events from resources.");
        } catch (JSONException e) {
            Log.e(TAG, "Can't parse events from resources.");
        }
        return null;
    }

    private static String readToString(InputStream is) throws IOException {
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
