package com.beactive.newevent;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beactive.R;

public class NewEventPagerAdapter extends FragmentPagerAdapter {
    public static final int PAGE_COMING_EVENTS = 0;
    public static final int PAGE_POPULAR_EVENTS = 1;
    private String[] mTitles;

    public NewEventPagerAdapter(Resources res, FragmentManager fragmentManager) {
        super(fragmentManager);
        mTitles = res.getStringArray(R.array.events_categories);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
 
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_COMING_EVENTS: {
                return new ComingEventsFragment();
            }
            case PAGE_POPULAR_EVENTS: {
                return new PopularEventsFragment();
            }
            default: {
                throw new IllegalArgumentException("Can't instantiate fragment with this index.");
            }
        }
    }
}