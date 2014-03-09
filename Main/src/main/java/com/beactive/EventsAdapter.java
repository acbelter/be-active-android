package com.beactive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<EventItem> {
    private LayoutInflater mInflater;

    public EventsAdapter(Context context, List<EventItem> items) {
        super(context, R.layout.item_event, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_event, null);

            ViewHolder holder = new ViewHolder();
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }

        EventItem item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.startTime.setText(item.getStartTimeString());
        holder.endTime.setText(item.getEndTimeString());
        holder.title.setText(item.getTitle());

        // FIXME Высота TextView может быть больше чем минимальная высота...
        convertView.setMinimumHeight(item.getViewHeight());

        return convertView;
    }
}