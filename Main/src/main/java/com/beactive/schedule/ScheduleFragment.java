package com.beactive.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beactive.R;
import com.beactive.core.BaseItem;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    private ArrayList<BaseItem> mScheduleEvents;
    private ScheduleView mScheduleView;
    private ScheduleAdapter mScheduleAdapter;

    public static ScheduleFragment newInstance(ArrayList<BaseItem> scheduleEvents) {
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.mScheduleEvents = scheduleEvents;
        scheduleFragment.setRetainInstance(true);
        return scheduleFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mScheduleAdapter = new ScheduleAdapter(getActivity(), mScheduleEvents);
        } catch (ScheduleAdapter.InvalidScheduleException e) {
            // FIXME
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        mScheduleView.setAdapter(mScheduleAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mScheduleView = (ScheduleView) view.findViewById(R.id.schedule);
        mScheduleView.initTimeMarks(8, 0, true);
        return view;
    }
}
