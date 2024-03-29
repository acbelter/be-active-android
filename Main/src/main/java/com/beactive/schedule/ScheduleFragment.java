package com.beactive.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.acbelter.scheduleview.ScheduleView;
import com.beactive.R;
import com.beactive.core.BaseItem;
import com.beactive.util.DateTimeUtils;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 0;
    private ArrayList<BaseItem> mScheduleEvents;
    private ScheduleView mScheduleView;
    private BeActiveScheduleAdapter mScheduleAdapter;

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
            mScheduleAdapter = new BeActiveScheduleAdapter(getActivity(),
                    mScheduleEvents, START_HOUR, END_HOUR,
                    DateTimeUtils.TIME_ZONE_OFFSET);
        } catch (BeActiveScheduleAdapter.InvalidScheduleException e) {
            // FIXME
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        mScheduleView.setAdapter(mScheduleAdapter);
        mScheduleView.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mScheduleView = (ScheduleView) view.findViewById(R.id.schedule);
        mScheduleView.configure(START_HOUR, END_HOUR, DateTimeUtils.TIME_ZONE_OFFSET, true);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Clicked: " +
                        mScheduleAdapter.getItem(position).getTitle(),
                Toast.LENGTH_SHORT).show();
    }
}
