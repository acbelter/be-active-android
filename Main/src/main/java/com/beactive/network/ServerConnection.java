package com.beactive.network;

import com.beactive.EventItem;

import java.util.List;

public interface ServerConnection {
    List<EventItem> getScheduleEvents();
    List<EventItem> getEvents();
}
