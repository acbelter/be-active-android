package com.beactive.schedule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beactive.R;
import com.beactive.destination.SelectDestinationActivity;
import com.beactive.network.ConnectionMock;
import com.beactive.network.ServerConnection;
import com.beactive.newevent.NewEventActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

public class ScheduleActivity extends ActionBarActivity {
    private ServerConnection mServerConnection;
    private WeekdaysPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Schedule mSchedule;
    // FIXME Variable for test
    private String mToastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // FIXME Testing implementation
        if (getIntent().getStringExtra("schedule") == null) {
            Intent intent = new Intent(this, SelectDestinationActivity.class);
            startActivity(intent);
            finish();
        } else {
            mToastText = getIntent().getStringExtra("schedule");
        }

        setContentView(R.layout.activity_schedule);

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

    @Override
    protected void onResume() {
        super.onResume();
        // FIXME Testing implementation
        if (mToastText != null) {
            Toast.makeText(this, mToastText, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_event: {
                Intent intent = new Intent(this, NewEventActivity.class);
                startActivity(intent);
                return true;
            } default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    // TODO
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // This is called when the Home (Up) button is pressed in the action bar.
//                // Create a simple intent that starts the hierarchical parent activity and
//                // use NavUtils in the Support Package to ensure proper handling of Up.
//                Intent upIntent = new Intent(this, ScheduleActivity.class);
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
