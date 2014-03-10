package com.beactive;

public class EventItem {
    private static final float MIN_EVENT_DURATION = 30*60*1000; // 30 min in millis
    private static final int MIN_EVENT_HEIGHT = 100;
    protected long eventId;
    protected long startTime;
    protected long endTime;
    protected String place;
    protected String title;
    protected String owner;
    protected EventType type;

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

    public void setType(EventType type) {
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

    public EventType getType() {
        return type;
    }

    public int getViewHeight() {
        float durationFactor = (endTime - startTime) / MIN_EVENT_DURATION;
        if (durationFactor < 1) {
            return MIN_EVENT_HEIGHT;
        } else {
            return (int) (durationFactor * MIN_EVENT_HEIGHT);
        }
    }
}
