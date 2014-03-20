package com.beactive.schedule;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Schedule {
    private static Calendar sCalendar = Calendar.getInstance();
    private SparseArray<List<IScheduleItem>> mSchedule;

    public Schedule(List<IScheduleItem> scheduleItems) {
        mSchedule = new SparseArray<List<IScheduleItem>>(7);
        mSchedule.append(Calendar.MONDAY, new ArrayList<IScheduleItem>());
        mSchedule.append(Calendar.TUESDAY, new ArrayList<IScheduleItem>());
        mSchedule.append(Calendar.WEDNESDAY, new ArrayList<IScheduleItem>());
        mSchedule.append(Calendar.THURSDAY, new ArrayList<IScheduleItem>());
        mSchedule.append(Calendar.FRIDAY, new ArrayList<IScheduleItem>());
        mSchedule.append(Calendar.SATURDAY, new ArrayList<IScheduleItem>());
        mSchedule.append(Calendar.SUNDAY, new ArrayList<IScheduleItem>());

        IScheduleItem item;
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

    public List<IScheduleItem> getScheduleFor(int weekday) {
        return mSchedule.get(weekday);
    }

    // TODO Insert LeisureScheduleItem after last ScheduleItem
    private void insertLeisureItems(List<IScheduleItem> schedule) {
        IScheduleItem current, next;
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
