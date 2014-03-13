package com.beactive.schedule;

import android.view.LayoutInflater;
import android.view.View;

import com.beactive.R;

public class LeisureScheduleItem implements BaseScheduleItem {
    protected long startTime;
    protected long endTime;

    public LeisureScheduleItem(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getViewHeight() {
        float durationFactor = (float) (endTime - startTime) / MIN_DURATION;
        // FIXME Test implementation
        return (int) (durationFactor * MIN_HEIGHT);
    }

    @Override
    public int getViewType() {
        return ITEM_TYPE_LEISURE;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_schedule_leisure, null);
        }
        convertView.setMinimumHeight(getViewHeight());
        convertView.setBackgroundColor(COLOR_LEISURE);
        return convertView;
    }
}
