package com.beactive.newevent;

import android.graphics.Bitmap;

import com.beactive.core.BaseItem;

public class PopularEventItem extends BaseItem {
    protected Bitmap mEventImage;

    public PopularEventItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
    }

    public boolean hasEventImage() {
        return mEventImage == null;
    }

    public Bitmap getEventImage() {
        return mEventImage;
    }
}
