package com.beactive.schedule;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beactive.R;
import com.beactive.core.BaseItem;

public class ScheduleItem extends BaseItem implements IScheduleItem {
    public ScheduleItem(long eventId, long startTime, long endTime) {
        super(eventId, startTime, endTime);
    }

    @Override
    public int getViewHeight() {
        float durationFactor = (float) (mEndTime - mStartTime) / MIN_DURATION;
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
        holder.timeLayout.setBackgroundColor(getTimeLayoutColor(mType));
        holder.startTime.setText(getStartTimeString());
        holder.endTime.setText(getEndTimeString());
        holder.title.setText(mTitle);
        holder.place.setText(mPlace);
        holder.owner.setText(mOwner);

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
