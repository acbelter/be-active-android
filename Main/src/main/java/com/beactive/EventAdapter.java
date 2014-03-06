package com.beactive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class EventAdapter extends ArrayAdapter<EventItem> {
    private LayoutInflater mInflater;

    public EventAdapter(Context context, List<EventItem> items) {
        super(context, R.layout.item_event, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_event, null);
        }

        return convertView;
    }
}