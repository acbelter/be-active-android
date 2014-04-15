package com.beactive.schedule;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

public interface IScheduleItem extends Parcelable {
    static final int ITEM_TYPES_COUNT = 2;
    static final int ITEM_TYPE_GENERAL = 0;
    static final int ITEM_TYPE_LEISURE = 1;

    int getViewHeight();
    int getViewType();
    View getView(LayoutInflater inflater, View convertView);
}