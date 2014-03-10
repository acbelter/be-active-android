package com.beactive;

import java.util.List;

public class SchedulePreparator {
    private List<EventItem> mScheduleEvents;

    public SchedulePreparator(List<EventItem> scheduleEvents) {
        mScheduleEvents = scheduleEvents;
    }

    // FIXME Testing implementation
    public List<EventItem> getSchedule(int pos) {
        return mScheduleEvents;
    }
}
