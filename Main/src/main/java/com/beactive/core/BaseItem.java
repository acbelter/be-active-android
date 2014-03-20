package com.beactive.core;

import com.beactive.schedule.ItemType;
import com.beactive.util.DateTimeUtils;

public class BaseItem {
    protected long mEventId;
    protected long mStartTime;
    protected long mEndTime;
    protected String mPlace;
    protected String mTitle;
    protected String mOwner;
    protected ItemType mType;

    public BaseItem(long eventId, long startTime, long endTime) {
        mEventId = eventId;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    public long getEventId() {
        return mEventId;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getStartTimeString() {
        return DateTimeUtils.parseTime(mStartTime);
    }

    public String getEndTimeString() {
        return DateTimeUtils.parseTime(mEndTime);
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public ItemType getType() {
        return mType;
    }

    public void setType(ItemType type) {
        mType = type;
    }

}
