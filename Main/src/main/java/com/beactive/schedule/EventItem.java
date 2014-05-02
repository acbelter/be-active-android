package com.beactive.schedule;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import com.beactive.core.BaseItem;

public class EventItem extends BaseItem {
    public EventItem(long id, long startTime, long endTime) {
        super(id, startTime, endTime);
    }

    private EventItem(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<EventItem> CREATOR =
            new Parcelable.Creator<EventItem>() {
                @Override
                public EventItem createFromParcel(Parcel in) {
                    return new EventItem(in);
                }

                @Override
                public EventItem[] newArray(int size) {
                    return new EventItem[size];
                }
            };

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        // TODO View in schedule
        return null;
    }
}
