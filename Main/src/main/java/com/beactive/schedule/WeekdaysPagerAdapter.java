package com.beactive.schedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseIntArray;

import com.beactive.R;

import java.util.Calendar;

public class WeekdaysPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mPageTitles;
    private Schedule mSchedule;
    private static SparseIntArray sPosWeekdayMap;

    static {
        sPosWeekdayMap = new SparseIntArray(7);
        sPosWeekdayMap.append(0, Calendar.MONDAY);
        sPosWeekdayMap.append(1, Calendar.TUESDAY);
        sPosWeekdayMap.append(2, Calendar.WEDNESDAY);
        sPosWeekdayMap.append(3, Calendar.THURSDAY);
        sPosWeekdayMap.append(4, Calendar.FRIDAY);
        sPosWeekdayMap.append(5, Calendar.SATURDAY);
        sPosWeekdayMap.append(6, Calendar.SUNDAY);
    }

    public WeekdaysPagerAdapter(Context context, FragmentManager fragmentManager, Schedule schedule) {
        super(fragmentManager);
        mPageTitles = context.getResources().getStringArray(R.array.weekdays);
        mSchedule = schedule;
    }

    @Override
    public Fragment getItem(int pos) {
        return ScheduleFragment.newInstance(mSchedule.getScheduleFor(sPosWeekdayMap.get(pos)));
    }

    @Override
    public int getCount() {
        return mPageTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        return mPageTitles[pos];
    }
}
