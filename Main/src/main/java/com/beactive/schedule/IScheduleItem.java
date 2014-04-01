package com.beactive.schedule;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

public interface IScheduleItem extends Parcelable {
    static final int ITEM_TYPES_COUNT = 2;
    static final int ITEM_TYPE_GENERAL = 0;
    static final int ITEM_TYPE_LEISURE = 1;

    static final int COLOR_LECTURE = 0xFFFF99CC;
    static final int COLOR_SEMINAR = 0xFFCCFFFF;
    // FIXME Change color name
    static final int COLOR_YELLOW = 0xFFFFFF99;
    static final int COLOR_LEISURE = 0xFFCCFFCC;

    static final long MIN_DURATION = 30*60*1000L; // 30 min in millis
    static final int MIN_HEIGHT = 100;

    int getViewHeight();
    int getViewType();
    View getView(LayoutInflater inflater, View convertView);
}