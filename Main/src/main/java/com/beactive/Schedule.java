package com.beactive;

import android.util.SparseArray;

import com.beactive.adapter.BaseScheduleItem;
import com.beactive.adapter.EventItem;
import com.beactive.adapter.LeisureScheduleItem;
import com.beactive.adapter.ScheduleItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Schedule {
    private static Calendar sCalendar = Calendar.getInstance();
    private SparseArray<List<BaseScheduleItem>> mSchedule;

    public Schedule(List<BaseScheduleItem> scheduleItems) {
        mSchedule = new SparseArray<List<BaseScheduleItem>>(7);
        mSchedule.append(Calendar.MONDAY, new ArrayList<BaseScheduleItem>());
        mSchedule.append(Calendar.TUESDAY, new ArrayList<BaseScheduleItem>());
        mSchedule.append(Calendar.WEDNESDAY, new ArrayList<BaseScheduleItem>());
        mSchedule.append(Calendar.THURSDAY, new ArrayList<BaseScheduleItem>());
        mSchedule.append(Calendar.FRIDAY, new ArrayList<BaseScheduleItem>());
        mSchedule.append(Calendar.SATURDAY, new ArrayList<BaseScheduleItem>());
        mSchedule.append(Calendar.SUNDAY, new ArrayList<BaseScheduleItem>());

        BaseScheduleItem item;
        for (int i = 0; i < scheduleItems.size(); i++) {
            item = scheduleItems.get(i);
            if (item.getClass() == ScheduleItem.class) {
                sCalendar.setTimeInMillis(((ScheduleItem) item).getStartTime());
                int weekday = sCalendar.get(Calendar.DAY_OF_WEEK);
                mSchedule.get(weekday).add(item);
            } else if (item.getClass() == EventItem.class) {
                // TODO For the future
            }
        }

        // Insert LeisureItems for every weekday
        for (int i = 0; i < mSchedule.size(); i++) {
            insertLeisureItems(mSchedule.valueAt(i));
        }
    }

    public List<BaseScheduleItem> getScheduleFor(int weekday) {
        return mSchedule.get(weekday);
    }

    // TODO Insert LeisureScheduleItem after last ScheduleItem
    private void insertLeisureItems(List<BaseScheduleItem> schedule) {
        BaseScheduleItem current, next;
        for (int i = 0; i < schedule.size() - 1; i++) {
            current = schedule.get(i);
            next = schedule.get(i + 1);

            if (current.getClass() == ScheduleItem.class &&
                    next.getClass() == ScheduleItem.class) {
                long startTime = ((ScheduleItem) current).getEndTime();
                long endTime = ((ScheduleItem) next).getStartTime();
                schedule.add(i + 1, new LeisureScheduleItem(startTime, endTime));
                // Skip new LeisureScheduleItem
                i++;
            }
        }
    }
}
