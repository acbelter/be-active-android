package com.beactive.newevent;

import android.graphics.Bitmap;

import com.beactive.core.BaseItem;
import com.beactive.util.DateTimeUtils;

public class ComingEventItem extends BaseItem {
    protected Bitmap mEventImage;

    public ComingEventItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
    }

    public String makeDatetimeDescription() {
        // TODO Check that start date equals end date
        return DateTimeUtils.parseDate(mStartTime) + "   " +
               DateTimeUtils.parseTime(mStartTime) + "-" + DateTimeUtils.parseTime(mEndTime);
    }

    public Bitmap getEventImage() {
        return mEventImage;
    }
}
