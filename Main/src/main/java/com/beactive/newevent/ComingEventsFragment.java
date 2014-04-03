package com.beactive.newevent;

import android.os.Bundle;

import com.beactive.R;
import com.devspark.progressfragment.ProgressListFragment;

// TODO Use custom ProgressFragment
public class ComingEventsFragment extends ProgressListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.no_coming_events);
        setListShown(false);
    }
}