package com.beactive.schedule;

import android.view.LayoutInflater;
import android.view.View;

import com.beactive.R;

public class LeisureScheduleItem implements IScheduleItem {
    protected long mStartTime;
    protected long mEndTime;

    public LeisureScheduleItem(long startTime, long endTime) {
        mStartTime = startTime;
        mEndTime = endTime;
    }

    @Override
    public int getViewHeight() {
        float durationFactor = (float) (mEndTime - mStartTime) / MIN_DURATION;
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
