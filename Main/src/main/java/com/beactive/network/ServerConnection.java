package com.beactive.network;

import com.beactive.adapter.BaseScheduleItem;
import com.beactive.adapter.EventItem;

import java.util.List;

public interface ServerConnection {
    List<BaseScheduleItem> getSchedule();
    List<EventItem> getEvents();
}
