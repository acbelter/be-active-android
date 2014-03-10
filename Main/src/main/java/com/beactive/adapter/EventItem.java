package com.beactive.adapter;

import com.beactive.DateTimeUtils;

public class EventItem {
    protected long eventId;
    protected long startTime;
    protected long endTime;
    protected String place;
    protected String title;
    protected String owner;
    protected ItemType type;

    public EventItem(long eventId, long startTime, long endTime) {
        this.eventId = eventId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getStartTimeString() {
        return DateTimeUtils.parseTime(startTime);
    }

    public String getEndTimeString() {
        return DateTimeUtils.parseTime(endTime);
    }

    public String getPlace() {
        return place;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public ItemType getType() {
        return type;
    }
}
