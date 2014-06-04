package com.beactive.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.acbelter.scheduleview.ScheduleAdapter;
import com.beactive.core.BaseItem;

import java.util.ArrayList;

public class BeActiveScheduleAdapter extends ScheduleAdapter<BaseItem> {
    private LayoutInflater mInflater;

    public BeActiveScheduleAdapter(Context context, ArrayList<BaseItem> items, int startHour,
                                   int endHour, int timeZoneOffset) throws
            InvalidScheduleException {
        super(context, items, startHour, endHour, timeZoneOffset);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return ScheduleConstants.ITEM_TYPES_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof ScheduleItem) {
            return ScheduleConstants.ITEM_TYPE_REGULAR;
        }
        if (getItem(position) instanceof EventItem) {
            return ScheduleConstants.ITEM_TYPE_EVENT;
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }
}
