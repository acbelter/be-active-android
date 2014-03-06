package com.beactive;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        if (layout != null) {
            layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
            layout.setAnchorPoint(0.3f);
            layout.setCoveredFadeColor(Color.TRANSPARENT);
        }

        List<ScheduleItem> scheduleData = new ArrayList<ScheduleItem>();
        for (int i = 0; i < 50; i++) {
            scheduleData.add(new ScheduleItem("09:00", "10:25", "Event in schedule " + i));
        }

        ListView schedule = (ListView) findViewById(R.id.schedule_list);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, scheduleData);
        schedule.setAdapter(scheduleAdapter);

        List<EventItem> eventsData = new ArrayList<EventItem>();
        for (int i = 0; i < 10; i++) {
            eventsData.add(new EventItem());
        }

        TwoWayView events = (TwoWayView) findViewById(R.id.events_list);
        EventAdapter eventsAdapter = new EventAdapter(this, eventsData);
        events.setAdapter(eventsAdapter);
    }
}
