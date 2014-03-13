package com.beactive.network;

import android.util.Pair;

import com.beactive.destination.DestinationItem;
import com.beactive.destination.DestinationsTree;
import com.beactive.schedule.BaseScheduleItem;
import com.beactive.schedule.EventItem;
import com.beactive.schedule.ItemType;
import com.beactive.schedule.ScheduleItem;

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

    public static DestinationsTree parseDestinationsTreeFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return null;
        }

        JSONObject root = new JSONObject(jsonStr);
        DestinationsTree destsTree = new DestinationsTree(root.getString("tree_title"));

        JSONArray structure = root.getJSONArray("structure");
        for (int i = 0; i < structure.length(); i++) {
            JSONObject obj = structure.getJSONObject(i);
            DestinationsTree.DestinationType type = DestinationsTree.DestinationType.valueOf(obj.getString("type"));
            Integer style = obj.getInt("style");
            destsTree.addStructureLevel(new Pair<DestinationsTree.DestinationType, Integer>(type, style));
        }

        JSONArray treeRoot = root.getJSONArray("tree");
        destsTree.setTree(processTree(treeRoot));

        return destsTree;
    }

    private static List<DestinationItem> processTree(JSONArray root) throws JSONException {
        if (root == null) {
            return new ArrayList<DestinationItem>(0);
        }

        List<DestinationItem> children = new ArrayList<DestinationItem>(root.length());
        for (int i = 0; i < root.length(); i++) {
            JSONObject child = root.getJSONObject(i);
            DestinationItem item = new DestinationItem(child.getString("title"));
            item.setImageLink(child.getString("image"));
            item.setElements(processTree(child.getJSONArray("elements")));
            children.add(item);
        }
        return children;
    }
}
