package com.beactive.network;

import com.beactive.core.BaseItem;
import com.beactive.core.ItemType;
import com.beactive.destination.DestinationItem;
import com.beactive.destination.DestinationRootItem;
import com.beactive.destination.DestinationsTree;
import com.beactive.newevent.ComingEventItem;
import com.beactive.newevent.PopularEventItem;
import com.beactive.schedule.EventItem;
import com.beactive.schedule.ScheduleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseParser {
    public static ArrayList<BaseItem> parseScheduleFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<BaseItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("schedule");
        ArrayList<BaseItem> schedule = new ArrayList<BaseItem>(jsonData.length());
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

    public static ArrayList<EventItem> parseEventsFromJson(String jsonStr) throws JSONException {
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

    public static ArrayList<ComingEventItem> parseComingEventsFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<ComingEventItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("coming_events");
        ArrayList<ComingEventItem> events = new ArrayList<ComingEventItem>(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            long id = jsonData.getJSONObject(i).getLong("id");
            long startTime = jsonData.getJSONObject(i).getLong("start");
            long endTime = jsonData.getJSONObject(i).getLong("end");

            ComingEventItem event = new ComingEventItem(id, startTime, endTime);
            event.setImageLink(jsonData.getJSONObject(i).getString("image"));
            event.setPlace(jsonData.getJSONObject(i).getString("place"));
            event.setTitle(jsonData.getJSONObject(i).getString("title"));
            event.setOwner(jsonData.getJSONObject(i).getString("owner"));
            event.setType(ItemType.parseFromInt(jsonData.getJSONObject(i).getInt("type")));

            events.add(event);
        }
        return events;
    }

    public static ArrayList<PopularEventItem> parsePopularEventsFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<PopularEventItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("popular_events");
        ArrayList<PopularEventItem> events = new ArrayList<PopularEventItem>(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            long id = jsonData.getJSONObject(i).getLong("id");
            long startTime = jsonData.getJSONObject(i).getLong("start");
            long endTime = jsonData.getJSONObject(i).getLong("end");

            PopularEventItem event = new PopularEventItem(id, startTime, endTime);
            event.setImageLink(jsonData.getJSONObject(i).getString("image"));
            event.setPlace(jsonData.getJSONObject(i).getString("place"));
            event.setTitle(jsonData.getJSONObject(i).getString("title"));
            event.setOwner(jsonData.getJSONObject(i).getString("owner"));
            event.setType(ItemType.parseFromInt(jsonData.getJSONObject(i).getInt("type")));

            events.add(event);
        }
        return events;
    }

    public static ArrayList<DestinationRootItem> parseDestinationsRootFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<DestinationRootItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("root");
        ArrayList<DestinationRootItem> items = new ArrayList<DestinationRootItem>(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            int id = jsonData.getJSONObject(i).getInt("id");
            String title = jsonData.getJSONObject(i).getString("title");

            DestinationRootItem item = new DestinationRootItem(id, title);
            items.add(item);
        }
        return items;
    }

    public static DestinationsTree parseDestinationsTreeFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return null;
        }

        JSONObject root = new JSONObject(jsonStr);
        DestinationsTree tree = new DestinationsTree(root.getInt("id"));

        JSONArray structure = root.getJSONArray("structure");
        JSONObject obj;
        int type;
        String title;
        int style;
        for (int i = 0; i < structure.length(); i++) {
            obj = structure.getJSONObject(i);
            type = obj.getInt("type");
            title = obj.getString("title");
            style = obj.getInt("style");
            tree.addStructureLevel(new DestinationsTree.StructureLevel(type, title, style));
        }

        JSONArray treeRoot = root.getJSONArray("tree");
        tree.setTree(processTree(treeRoot));

        return tree;
    }

    private static ArrayList<DestinationItem> processTree(JSONArray root) throws JSONException {
        if (root == null) {
            return new ArrayList<DestinationItem>(0);
        }

        ArrayList<DestinationItem> children = new ArrayList<DestinationItem>(root.length());
        JSONObject child;
        DestinationItem item;
        ArrayList<DestinationItem> elements;
        for (int i = 0; i < root.length(); i++) {
            child = root.getJSONObject(i);
            item = new DestinationItem(child.getString("title"));

            if (!child.isNull("image")) {
                item.setImageLink(child.getString("image"));
            }

            if (!child.isNull("elements")) {
                elements = processTree(child.getJSONArray("elements"));
            } else {
                elements = null;
            }

            item.setElements(elements);
            children.add(item);
        }
        return children;
    }
}
