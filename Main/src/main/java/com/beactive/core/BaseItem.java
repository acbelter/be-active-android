package com.beactive.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import com.beactive.util.DateTimeUtils;

public abstract class BaseItem implements Parcelable, Comparable<BaseItem> {
    protected long mId;
    protected long mStartTime;
    protected long mEndTime;
    protected String mPlace;
    protected String mTitle;
    protected String mOwner;
    protected ItemType mType;

    public BaseItem(long id, long startTime, long endTime) {
        mId = id;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    protected BaseItem(Parcel in) {
        mId = in.readLong();
        mStartTime = in.readLong();
        mEndTime = in.readLong();
        mPlace = in.readString();
        mTitle = in.readString();
        mOwner = in.readString();
        mType = (ItemType) in.readSerializable();
    }

    public View getView(LayoutInflater inflater, View convertView) {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(mId);
        out.writeLong(mStartTime);
        out.writeLong(mEndTime);
        out.writeString(mPlace);
        out.writeString(mTitle);
        out.writeString(mOwner);
        out.writeSerializable(mType);
    }

    public long getId() {
        return mId;
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

    @Override
    public int compareTo(BaseItem another) {
        if (mStartTime < another.mStartTime) {
            return -1;
        }
        if (mStartTime > another.mStartTime) {
            return 1;
        }
        return 0;
    }
}
