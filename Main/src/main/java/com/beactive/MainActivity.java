package com.beactive;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.beactive.adapter.EventItem;
import com.beactive.adapter.EventsAdapter;
import com.beactive.network.ConnectionMock;
import com.beactive.network.ServerConnection;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

public class MainActivity extends FragmentActivity {
    private ServerConnection mServerConnection;
    private WeekdaysPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Schedule mSchedule;

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

        mSchedule = new Schedule(mServerConnection.getSchedule());

        mViewPager = (ViewPager) findViewById(R.id.schedule_pager);
        mPagerAdapter = new WeekdaysPagerAdapter(this, getSupportFragmentManager(), mSchedule);
        mViewPager.setAdapter(mPagerAdapter);

        List<EventItem> events = mServerConnection.getEvents();
        TwoWayView eventsView = (TwoWayView) findViewById(R.id.events_list);
        EventsAdapter eventsAdapter = new EventsAdapter(this, events);
        eventsView.setAdapter(eventsAdapter);
    }

    // TODO
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // This is called when the Home (Up) button is pressed in the action bar.
//                // Create a simple intent that starts the hierarchical parent activity and
//                // use NavUtils in the Support Package to ensure proper handling of Up.
//                Intent upIntent = new Intent(this, MainActivity.class);
//                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                    // This activity is not part of the application's task, so create a new task
//                    // with a synthesized back stack.
//                    TaskStackBuilder.from(this)
//                            // If there are ancestor activities, they should be added here.
//                            .addNextIntent(upIntent)
//                            .startActivities();
//                    finish();
//                } else {
//                    // This activity is part of the application's task, so simply
//                    // navigate up to the hierarchical parent activity.
//                    NavUtils.navigateUpTo(this, upIntent);
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
