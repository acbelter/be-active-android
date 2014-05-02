package com.beactive.schedule;

import android.util.SparseArray;

import com.beactive.core.BaseItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Schedule {
    private static Calendar sCalendar = Calendar.getInstance();
    private SparseArray<ArrayList<BaseItem>> mSchedule;

    public Schedule(List<BaseItem> scheduleItems) {
        mSchedule = new SparseArray<ArrayList<BaseItem>>(7);
        mSchedule.append(Calendar.MONDAY, new ArrayList<BaseItem>());
        mSchedule.append(Calendar.TUESDAY, new ArrayList<BaseItem>());
        mSchedule.append(Calendar.WEDNESDAY, new ArrayList<BaseItem>());
        mSchedule.append(Calendar.THURSDAY, new ArrayList<BaseItem>());
        mSchedule.append(Calendar.FRIDAY, new ArrayList<BaseItem>());
        mSchedule.append(Calendar.SATURDAY, new ArrayList<BaseItem>());
        mSchedule.append(Calendar.SUNDAY, new ArrayList<BaseItem>());

        BaseItem item;
        for (int i = 0; i < scheduleItems.size(); i++) {
            item = scheduleItems.get(i);
            if (item instanceof ScheduleItem) {
                sCalendar.setTimeInMillis(item.getStartTime());
                int weekday = sCalendar.get(Calendar.DAY_OF_WEEK);
                mSchedule.get(weekday).add(item);
            } else if (item instanceof EventItem) {
                // TODO For the future
            }
        }
    }

    public ArrayList<BaseItem> getScheduleFor(int weekday) {
        return mSchedule.get(weekday);
    }
}
