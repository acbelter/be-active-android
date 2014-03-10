package com.beactive;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class WeekdaysPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mPageTitles;
    private SchedulePreparator mSchedulePreparator;

    public WeekdaysPagerAdapter(Context context, FragmentManager fragmentManager,
                                SchedulePreparator schedulePreparator) {
        super(fragmentManager);
        mPageTitles = context.getResources().getStringArray(R.array.weekdays);
        mSchedulePreparator = schedulePreparator;
    }

    @Override
    public Fragment getItem(int pos) {
        return ScheduleFragment.newInstance(mSchedulePreparator.getSchedule(pos));
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
