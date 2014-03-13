package com.beactive.network;

import com.beactive.destination.DestinationsTree;
import com.beactive.schedule.BaseScheduleItem;
import com.beactive.schedule.EventItem;

import java.util.List;

public interface ServerConnection {
    List<BaseScheduleItem> getSchedule();
    List<EventItem> getEvents();
    DestinationsTree getDestinationsTree();
}
