package com.beactive.network;

import com.beactive.adapter.BaseScheduleItem;
import com.beactive.adapter.EventItem;
import com.beactive.adapter.ItemType;
import com.beactive.adapter.ScheduleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
    public static List<BaseScheduleItem> parseScheduleFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<BaseScheduleItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("schedule");
        List<BaseScheduleItem> schedule = new ArrayList<BaseScheduleItem>(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            long id = jsonData.getJSONObject(i).getLong("id");
            long startTime = jsonData.getJSONObject(i).getLong("start");
            long endTime = jsonData.getJSONObject(i).getLong("end");

            ScheduleItem scheduleEvent = new ScheduleItem(id, startTime, endTime);
            scheduleEvent.setPlace(jsonData.getJSONObject(i).getString("place"));
            scheduleEvent.setTitle(jsonData.getJSONObject(i).getString("title"));
            scheduleEvent.setOwner(jsonData.getJSONObject(i).getString("owner"));
            scheduleEvent.setType(ItemType.parseFromInt(jsonData.getJSONObject(i).getInt("type")));

            schedule.add(scheduleEvent);
        }
        return schedule;
    }

    public static List<EventItem> parseEventsFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<EventItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("events");
        ArrayList<EventItem> events = new ArrayList<EventItem>(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            long id = jsonData.getJSONObject(i).getLong("id");
            long startTime = jsonData.getJSONObject(i).getLong("start");
            long endTime = jsonData.getJSONObject(i).getLong("end");

            EventItem event = new EventItem(id, startTime, endTime);
            event.setPlace(jsonData.getJSONObject(i).getString("place"));
            event.setTitle(jsonData.getJSONObject(i).getString("title"));
            event.setOwner(jsonData.getJSONObject(i).getString("owner"));
            event.setType(ItemType.parseFromInt(jsonData.getJSONObject(i).getInt("type")));

            events.add(event);
        }
        return events;
    }
}
