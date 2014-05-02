package com.beactive.schedule;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beactive.R;
import com.beactive.core.BaseItem;
import com.beactive.core.BeActiveActivity;
import com.beactive.destination.SelectDestinationActivity;
import com.beactive.network.ResponseParser;
import com.beactive.network.command.GetEventsCommand;
import com.beactive.network.command.GetScheduleCommand;
import com.beactive.newevent.NewEventActivity;
import com.beactive.util.PrefUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class ScheduleActivity extends BeActiveActivity {
    private ViewPager mSchedulePager;
    private TwoWayView mEventsList;

    private SharedPreferences mPrefs;

    private Schedule mSchedule;
    private ArrayList<EventItem> mEvents;
    private WeekdaysPagerAdapter mPagerAdapter;
    private EventsAdapter mEventsAdapter;

    private int mScheduleRequestId = -1;
    private int mEventsRequestId = -1;

    private int mRootId;
    private String mDestinationsPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!mPrefs.contains(PrefUtils.KEY_ROOT_ID) ||
                !mPrefs.contains(PrefUtils.KEY_DESTINATIONS_PATH)) {
            // Show configuration screen
            Intent intent = new Intent(this, SelectDestinationActivity.class);
            startActivity(intent);
            finish();
        } else {
            mRootId = mPrefs.getInt(PrefUtils.KEY_ROOT_ID, -1);
            mDestinationsPath = mPrefs.getString(PrefUtils.KEY_DESTINATIONS_PATH, null);
        }

        setContentView(R.layout.activity_schedule);

        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        if (layout != null) {
            layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
            layout.setAnchorPoint(1.0f);
            layout.setCoveredFadeColor(Color.TRANSPARENT);
        }

        mSchedulePager = (ViewPager) findViewById(R.id.schedule_pager);
        mEventsList = (TwoWayView) findViewById(R.id.events_list);

        if (savedInstanceState == null) {
            if (mPrefs.contains(PrefUtils.KEY_SCHEDULE)) {
                String scheduleJson = mPrefs.getString(PrefUtils.KEY_SCHEDULE, null);
                if (scheduleJson != null) {
                    try {
                        ArrayList<BaseItem> schedule = ResponseParser.parseScheduleFromJson(scheduleJson);
                        if (schedule != null) {
                            setupSchedule(new Schedule(schedule));
                        }
                    } catch (JSONException e) {
                        // TODO Translate message
                        Toast.makeText(getApplicationContext(), "Can't open schedule from preferences.", Toast.LENGTH_LONG);
                    }
                }
            }

            // Now start loading events
            mEventsRequestId = getNetworkServiceHelper().getEvents(mRootId);
        } else {
            mScheduleRequestId = savedInstanceState.getInt("schedule_request_id", -1);
            mEventsRequestId = savedInstanceState.getInt("events_request_id", -1);

            Schedule schedule = (Schedule) getLastCustomNonConfigurationInstance();
            if (schedule != null) {
                setupSchedule(schedule);
            }

            ArrayList<EventItem> events = savedInstanceState.getParcelableArrayList("events");
            if (events == null) {
                events = new ArrayList<EventItem>(0);
            }
            setupEvents(events);
        }
    }

    private void setupSchedule(Schedule schedule) {
        mSchedule = schedule;
        mPagerAdapter = new WeekdaysPagerAdapter(ScheduleActivity.this,
                getSupportFragmentManager(), mSchedule);
        mSchedulePager.setAdapter(mPagerAdapter);
    }

    private void setupEvents(ArrayList<EventItem> events) {
        mEvents = events;
        mEventsAdapter = new EventsAdapter(ScheduleActivity.this, events);
        mEventsList.setAdapter(mEventsAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("schedule_request_id", mScheduleRequestId);
        outState.putInt("events_request_id", mEventsRequestId);
        outState.putParcelableArrayList("events", mEvents);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mSchedule;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mPrefs.contains(PrefUtils.KEY_SCHEDULE)) {
            if (mScheduleRequestId == -1) {
                // Download new schedule
                LoadingDialogFragment loading = new LoadingDialogFragment();
                loading.show(getSupportFragmentManager(), LoadingDialogFragment.class.getSimpleName());
                mScheduleRequestId = getNetworkServiceHelper().getSchedule(mRootId, mDestinationsPath);
            } else if (!getNetworkServiceHelper().isPending(mScheduleRequestId)) {
                dismissLoadingDialog();
            }
        }
    }

    private void dismissLoadingDialog() {
        LoadingDialogFragment loading =
                (LoadingDialogFragment) getSupportFragmentManager()
                        .findFragmentByTag(LoadingDialogFragment.class.getSimpleName());
        if (loading != null) {
            loading.dismiss();
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
            }
            case R.id.item_clear_cache: {
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.remove(PrefUtils.KEY_ROOT_ID);
                editor.remove(PrefUtils.KEY_DESTINATIONS_PATH);
                editor.remove(PrefUtils.KEY_SCHEDULE);
                editor.commit();

                // FIXME Fix message
                Toast.makeText(getApplicationContext(), "Cache cleared.", Toast.LENGTH_SHORT).show();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onServiceCallback(int requestId, Intent requestIntent, int resultCode, Bundle data) {
        if (getNetworkServiceHelper().checkCommandClass(requestIntent, GetScheduleCommand.class)) {
            if (resultCode == GetScheduleCommand.RESPONSE_SUCCESS) {
                dismissLoadingDialog();

                String scheduleJson = data.getString("schedule_json");
                // Save schedule
                if (scheduleJson != null) {
                    mPrefs.edit().putString(PrefUtils.KEY_SCHEDULE, scheduleJson).commit();
                }

                ArrayList<BaseItem> schedule = data.getParcelableArrayList("schedule");
                if (schedule != null) {
                    setupSchedule(new Schedule(schedule));

                    // Now start loading events
                    if (mEvents == null) {
                        mEventsRequestId = getNetworkServiceHelper().getEvents(mRootId);
                    }
                } else {
                    // FIXME Fix message
                    Toast.makeText(getApplicationContext(), "Can't load schedule.", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == GetScheduleCommand.RESPONSE_PROGRESS) {
                // TODO For the future
            } else if (resultCode == GetScheduleCommand.RESPONSE_FAILURE) {
                dismissLoadingDialog();
                // FIXME Fix message
                Toast.makeText(getApplicationContext(), "Can't load schedule.", Toast.LENGTH_LONG).show();
            }
        }

        if (getNetworkServiceHelper().checkCommandClass(requestIntent, GetEventsCommand.class)) {
            if (resultCode == GetEventsCommand.RESPONSE_SUCCESS) {
                ArrayList<EventItem> events = data.getParcelableArrayList("events");
                if (events != null) {
                    setupEvents(events);
                } else {
                    events = new ArrayList<EventItem>(0);
                    setupEvents(events);
                    // FIXME Fix message
                    Toast.makeText(getApplicationContext(), "Can't load events.", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == GetEventsCommand.RESPONSE_PROGRESS) {
                // TODO For the future
            } else if (resultCode == GetScheduleCommand.RESPONSE_FAILURE) {
                // FIXME Fix message
                Toast.makeText(getApplicationContext(), "Can't load events.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO Move this class to BeActiveActivity?
    public static class LoadingDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            return progressDialog;
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
