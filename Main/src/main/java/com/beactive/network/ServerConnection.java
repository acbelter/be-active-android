package com.beactive.network;

import com.beactive.destination.DestinationsTree;
import com.beactive.newevent.ComingEventItem;
import com.beactive.schedule.EventItem;
import com.beactive.schedule.IScheduleItem;

import java.util.List;

public interface ServerConnection {
    List<IScheduleItem> getSchedule();
    List<EventItem> getEvents();
    List<ComingEventItem> getComingEvents();
    DestinationsTree getDestinationsTree();
}
