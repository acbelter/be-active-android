package com.beactive.schedule;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.beactive.R;
import com.beactive.core.BaseItem;
import com.beactive.core.ItemType;

public class ScheduleItem extends BaseItem {
    public ScheduleItem(long id, long startTime, long endTime) {
        super(id, startTime, endTime);
    }

    private ScheduleItem(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<ScheduleItem> CREATOR =
            new Parcelable.Creator<ScheduleItem>() {
                @Override
                public ScheduleItem createFromParcel(Parcel in) {
                    return new ScheduleItem(in);
                }

                @Override
                public ScheduleItem[] newArray(int size) {
                    return new ScheduleItem[size];
                }
            };

    static class ViewHolder {
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
            holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
            holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.place = (TextView) convertView.findViewById(R.id.place);
            holder.owner = (TextView) convertView.findViewById(R.id.owner);
            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.startTime.setText(getStartTimeString());
        holder.endTime.setText(getEndTimeString());
        holder.title.setBackgroundColor(getTimeLayoutColor(mType));
        holder.title.setText(mTitle);
        holder.place.setText(mPlace);
        holder.owner.setText(mOwner);

        return convertView;
    }

    private int getTimeLayoutColor(ItemType type) {
        switch (type) {
            case LECTURE: {
                return ScheduleConstants.COLOR_LECTURE;
            }
            case SEMINAR: {
                return ScheduleConstants.COLOR_SEMINAR;
            }
            case PRACTICE: {
                return ScheduleConstants.COLOR_PRACTICE;
            }
            default: {
                return ScheduleConstants.COLOR_LEISURE;
            }
        }
    }
}
