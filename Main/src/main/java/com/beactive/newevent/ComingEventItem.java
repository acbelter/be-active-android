package com.beactive.newevent;

import android.os.Parcel;
import android.os.Parcelable;

import com.beactive.core.BaseItem;
import com.beactive.util.DateTimeUtils;

public class ComingEventItem extends BaseItem {
    protected String mImageLink;

    public ComingEventItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
    }

    private ComingEventItem(Parcel in) {
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

    public static final Parcelable.Creator<ComingEventItem> CREATOR =
            new Parcelable.Creator<ComingEventItem>() {
                @Override
                public ComingEventItem createFromParcel(Parcel in) {
                    return new ComingEventItem(in);
                }

                @Override
                public ComingEventItem[] newArray(int size) {
                    return new ComingEventItem[size];
                }
            };

    public String makeDatetimeDescription() {
        // TODO Check that start date equals end date
        return DateTimeUtils.parseDate(mStartTime) + "   " +
               DateTimeUtils.parseTime(mStartTime) + "-" + DateTimeUtils.parseTime(mEndTime);
    }
}
