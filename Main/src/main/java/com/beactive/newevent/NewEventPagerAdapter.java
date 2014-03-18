package com.beactive.newevent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewEventPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public NewEventPagerAdapter(FragmentManager fragmentManager, String[] titles) {
        super(fragmentManager);
        mTitles = titles;
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
            case 0: {
                return new ComingEventsFragment();
            }
            case 1: {
                return new PopularEventsFragment();
            }
            default: {
                throw new IllegalArgumentException("Can't instantiate fragment with this index.");
            }
        }
    }
}