package com.beactive.network;

import com.beactive.destination.DestinationItem;
import com.beactive.destination.DestinationsTree;
import com.beactive.newevent.ComingEventItem;
import com.beactive.schedule.EventItem;
import com.beactive.schedule.IScheduleItem;
import com.beactive.schedule.ItemType;
import com.beactive.schedule.ScheduleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseDataParser {
    public static List<IScheduleItem> parseScheduleFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<IScheduleItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("schedule");
        List<IScheduleItem> schedule = new ArrayList<IScheduleItem>(jsonData.length());
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

    // FIXME This testing implementation doesn't parse image links
    public static List<ComingEventItem> parseComingEventsFromJson(String jsonStr) throws JSONException {
        if (jsonStr == null) {
            return new ArrayList<ComingEventItem>(0);
        }

        JSONArray jsonData = new JSONObject(jsonStr).getJSONArray("events");
        ArrayList<ComingEventItem> events = new ArrayList<ComingEventItem>(jsonData.length());
        for (int i = 0; i < jsonData.length(); i++) {
            long id = jsonData.getJSONObject(i).getLong("id");
            long startTime = jsonData.getJSONObject(i).getLong("start");
            long endTime = jsonData.getJSONObject(i).getLong("end");

            ComingEventItem event = new ComingEventItem(id, startTime, endTime);
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
        DestinationsTree destsTree = new DestinationsTree(root.getString("title"));

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
            destsTree.addStructureLevel(new DestinationsTree.StructureLevel(type, title, style));
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
        JSONObject child;
        DestinationItem item;
        List<DestinationItem> elements;
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
