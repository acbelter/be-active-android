package com.beactive.schedule;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beactive.R;

import java.util.List;

public class ScheduleFragment extends ListFragment {
    private List<BaseScheduleItem> mScheduleEvents;
    private ScheduleAdapter mScheduleAdapter;

    public static ScheduleFragment newInstance(List<BaseScheduleItem> scheduleEvents) {
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.mScheduleEvents = scheduleEvents;
        scheduleFragment.setRetainInstance(true);
        return scheduleFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScheduleAdapter = new ScheduleAdapter(getActivity(), mScheduleEvents);
        setListAdapter(mScheduleAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}
