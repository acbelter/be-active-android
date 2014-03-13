package com.beactive.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<BaseScheduleItem> {
    private LayoutInflater mInflater;

    public ScheduleAdapter(Context context, List<BaseScheduleItem> items) {
        super(context, 0, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return BaseScheduleItem.ITEM_TYPES_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }
}
