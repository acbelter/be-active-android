package com.beactive.newevent;

import android.os.Parcel;
import android.os.Parcelable;

import com.beactive.core.BaseItem;

public class PopularEventItem extends BaseItem {
    protected String mImageLink;

    public PopularEventItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
    }

    private PopularEventItem(Parcel in) {
        super(in);
        mImageLink = in.readString();
    }

    public String getImageLink() {
        return mImageLink;
    }

    public void setImageLink(String imageLink) {
        mImageLink = imageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mImageLink);
    }

    public static final Parcelable.Creator<PopularEventItem> CREATOR =
            new Parcelable.Creator<PopularEventItem>() {
                @Override
                public PopularEventItem createFromParcel(Parcel in) {
                    return new PopularEventItem(in);
                }

                @Override
                public PopularEventItem[] newArray(int size) {
                    return new PopularEventItem[size];
                }
            };
}
