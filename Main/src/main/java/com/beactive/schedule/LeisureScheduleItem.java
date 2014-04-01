package com.beactive.schedule;

import android.os.Parcel;
import android.os.Parcelable;
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

    private LeisureScheduleItem(Parcel in) {
        mStartTime = in.readLong();
        mEndTime = in.readLong();
    }

    public static final Parcelable.Creator<LeisureScheduleItem> CREATOR =
            new Parcelable.Creator<LeisureScheduleItem>() {
                @Override
                public LeisureScheduleItem createFromParcel(Parcel in) {
                    return new LeisureScheduleItem(in);
                }

                @Override
                public LeisureScheduleItem[] newArray(int size) {
                    return new LeisureScheduleItem[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(mStartTime);
        out.writeLong(mEndTime);
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
