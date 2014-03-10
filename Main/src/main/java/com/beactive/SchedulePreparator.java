package com.beactive;

import com.beactive.adapter.BaseScheduleItem;
import com.beactive.adapter.LeisureScheduleItem;
import com.beactive.adapter.ScheduleItem;

import java.util.List;

// FIXME Testing implementation
public class SchedulePreparator {
    private List<BaseScheduleItem> mSchedule;

    public SchedulePreparator(List<BaseScheduleItem> schedule) {
        mSchedule = schedule;
        insertLeisureItems(mSchedule);
    }

    public List<BaseScheduleItem> getSchedule(int pos) {
        return mSchedule;
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
