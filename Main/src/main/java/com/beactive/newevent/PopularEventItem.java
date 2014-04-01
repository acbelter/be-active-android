package com.beactive.newevent;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.beactive.core.BaseItem;

public class PopularEventItem extends BaseItem {
    protected Bitmap mEventImage;

    public PopularEventItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
    }

    private PopularEventItem(Parcel in) {
        super(in);
        mEventImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(mEventImage, flags);
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

    public boolean hasEventImage() {
        return mEventImage == null;
    }

    public Bitmap getEventImage() {
        return mEventImage;
    }
}
