package com.beactive.schedule;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beactive.R;
import com.beactive.util.DateTimeUtils;

public class ScheduleItem implements BaseScheduleItem {
    protected long eventId;
    protected long startTime;
    protected long endTime;
    protected String place;
    protected String title;
    protected String owner;
    protected ItemType type;

    public ScheduleItem(long eventId, long startTime, long endTime) {
        this.eventId = eventId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getStartTimeString() {
        return DateTimeUtils.parseTime(startTime);
    }

    public String getEndTimeString() {
        return DateTimeUtils.parseTime(endTime);
    }

    public String getPlace() {
        return place;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public int getViewHeight() {
        float durationFactor = (float) (endTime - startTime) / MIN_DURATION;
        if (durationFactor < 1) {
            return MIN_HEIGHT;
        } else {
            return (int) (durationFactor * MIN_HEIGHT);
        }
    }

    @Override
    public int getViewType() {
        return ITEM_TYPE_GENERAL;
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
    public View getView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_schedule, null);

            ViewHolder holder = new ViewHolder();
            holder.timeLayout = (RelativeLayout) convertView.findViewById(R.id.time_layout);
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.place = (TextView) convertView.findViewById(R.id.place);
            holder.owner = (TextView) convertView.findViewById(R.id.owner);
            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.timeLayout.setBackgroundColor(getTimeLayoutColor(type));
        holder.startTime.setText(getStartTimeString());
        holder.endTime.setText(getEndTimeString());
        holder.title.setText(title);
        holder.place.setText(place);
        holder.owner.setText(owner);

        // FIXME Height of title TextView can be more than the minimal height of item...
        convertView.setMinimumHeight(getViewHeight());

        return convertView;
    }

    private int getTimeLayoutColor(ItemType type) {
        switch (type) {
            case LECTURE: {
                return COLOR_LECTURE;
            }
            case SEMINAR: {
                return COLOR_SEMINAR;
            }
            case LAB: {
                return COLOR_YELLOW;
            }
            default: {
                return Color.TRANSPARENT;
            }
        }
    }
}
