package com.beactive.schedule;

import android.view.LayoutInflater;
import android.view.View;

import com.beactive.core.BaseItem;

public class EventItem extends BaseItem implements IScheduleItem {
    public EventItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
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
