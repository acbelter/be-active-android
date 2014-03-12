package com.beactive.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.beactive.DateTimeUtils;

public class EventItem implements BaseScheduleItem {
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

    @Override
    public int getViewHeight() {
        // TODO
        return 0;
    }

    @Override
    public int getViewType() {
        // FIXME
        return -1;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        // TODO View in schedule
        return null;
    }
}
