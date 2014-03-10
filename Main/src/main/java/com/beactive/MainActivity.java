package com.beactive;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.beactive.network.ConnectionMock;
import com.beactive.network.ServerConnection;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

public class MainActivity extends Activity {
    private ServerConnection mServerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        if (layout != null) {
            layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
            layout.setAnchorPoint(1.0f);
            layout.setCoveredFadeColor(Color.TRANSPARENT);
        }

        mServerConnection = new ConnectionMock(this);

        List<EventItem> scheduleEventsData = mServerConnection.getScheduleEvents();
        List<EventItem> eventsData = mServerConnection.getEvents();

        ListView schedule = (ListView) findViewById(R.id.schedule_list);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this, scheduleEventsData);
        schedule.setAdapter(scheduleAdapter);

        TwoWayView events = (TwoWayView) findViewById(R.id.events_list);
        EventsAdapter eventsAdapter = new EventsAdapter(this, eventsData);
        events.setAdapter(eventsAdapter);
    }
}
