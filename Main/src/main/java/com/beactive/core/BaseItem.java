package com.beactive.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.beactive.util.DateTimeUtils;

public abstract class BaseItem implements Parcelable {
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

    protected BaseItem(Parcel in) {
        mEventId = in.readLong();
        mStartTime = in.readLong();
        mEndTime = in.readLong();
        mPlace = in.readString();
        mTitle = in.readString();
        mOwner = in.readString();
        mType = (ItemType) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(mEventId);
        out.writeLong(mStartTime);
        out.writeLong(mEndTime);
        out.writeString(mPlace);
        out.writeString(mTitle);
        out.writeString(mOwner);
        out.writeSerializable(mType);
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
