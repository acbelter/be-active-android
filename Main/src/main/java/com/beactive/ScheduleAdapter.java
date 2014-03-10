package com.beactive;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<EventItem> {
    private LayoutInflater mInflater;
    private Resources mResources;

    public ScheduleAdapter(Context context, List<EventItem> items) {
        super(context, R.layout.item_schedule_event, items);
        mResources = context.getResources();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        RelativeLayout timeLayout;
        TextView startTime;
        TextView endTime;
        TextView title;
        TextView place;
        TextView owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_schedule_event, null);

            ViewHolder holder = new ViewHolder();
            holder.timeLayout = (RelativeLayout) convertView.findViewById(R.id.time_layout);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.place = (TextView) convertView.findViewById(R.id.place);
            holder.owner = (TextView) convertView.findViewById(R.id.owner);
            convertView.setTag(holder);
        }

        EventItem item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.timeLayout.setBackgroundColor(getTimeLayoutColor(item.getType()));
        holder.startTime.setText(item.getStartTimeString());
        holder.endTime.setText(item.getEndTimeString());
        holder.title.setText(item.getTitle());
        holder.place.setText(item.getTitle());
        holder.owner.setText(item.getOwner());

        // FIXME Height of title TextView can be more than the minimal height of item...
        convertView.setMinimumHeight(item.getViewHeight());

        return convertView;
    }

    private int getTimeLayoutColor(EventType type) {
        switch (type) {
            case LECTURE: {
                return mResources.getColor(R.color.color_lecture);
            }
            case SEMINAR: {
                return mResources.getColor(R.color.color_seminar);
            }
            case LAB: {
                // TODO Rename color
                return mResources.getColor(R.color.color_yellow);
            }
            default: {
                return Color.TRANSPARENT;
            }
        }
    }
}
