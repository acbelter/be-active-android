package com.beactive.newevent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.beactive.R;
import com.beactive.core.BeActiveActivity;
import com.beactive.network.command.GetComingEventsCommand;
import com.beactive.network.command.GetPopularEventsCommand;
import com.beactive.util.PrefUtils;

import java.util.ArrayList;

public class NewEventActivity extends BeActiveActivity implements SearchView.OnQueryTextListener {
    private SearchView mSearchView;
    private ViewPager mPager;
    private NewEventPagerAdapter mPagerAdapter;
    private SharedPreferences mPrefs;

    private int mRootId;
    // FIXME For the future?
    private String mDestinationsPath;

    private int mComingEventsRequestId = -1;
    private int mPopularEventsRequestId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!mPrefs.contains(PrefUtils.KEY_ROOT_ID) ||
                !mPrefs.contains(PrefUtils.KEY_DESTINATIONS_PATH)) {
            // FIXME Handle this case
            Toast.makeText(getApplicationContext(), "Can't load preferences.", Toast.LENGTH_LONG).show();
        } else {
            mRootId = mPrefs.getInt(PrefUtils.KEY_ROOT_ID, -1);
            mDestinationsPath = mPrefs.getString(PrefUtils.KEY_DESTINATIONS_PATH, null);
        }

        setContentView(R.layout.activity_new_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.pager);

        mPagerAdapter = new NewEventPagerAdapter(getResources(), getSupportFragmentManager());

        mPager.setAdapter(mPagerAdapter);
        tabs.setViewPager(mPager);

        if (savedInstanceState == null) {
            // FIXME
        } else {
            mComingEventsRequestId = savedInstanceState.getInt("coming_events_request_id");
            mPopularEventsRequestId = savedInstanceState.getInt("popular_events_request_id");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRootId != -1 && mDestinationsPath != null) {
            if (mComingEventsRequestId == -1) {
                // Download coming events
                mComingEventsRequestId = getNetworkServiceHelper().getComingEvents(mRootId);
            } else if (!getNetworkServiceHelper().isPending(mComingEventsRequestId)) {
                ComingEventsFragment cef = getComingEventsFragment();
                if (cef != null) {
                    cef.setListShown(true);
                }
            }

            if (mPopularEventsRequestId == -1) {
                // Download popular events
                mPopularEventsRequestId = getNetworkServiceHelper().getPopularEvents(mRootId);
            } else if (!getNetworkServiceHelper().isPending(mPopularEventsRequestId)) {
                PopularEventsFragment pef = getPopularEventsFragment();
                if (pef != null) {
                    pef.setListShown(true);
                }
            }
        }
    }

    public ComingEventsFragment getComingEventsFragment() {
        return (ComingEventsFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + mPager.getId() + ":"
                        + mPagerAdapter.getItemId(NewEventPagerAdapter.PAGE_COMING_EVENTS));
    }

    public PopularEventsFragment getPopularEventsFragment() {
        return (PopularEventsFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + mPager.getId() + ":"
                        + mPagerAdapter.getItemId(NewEventPagerAdapter.PAGE_POPULAR_EVENTS));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("coming_events_request_id", mComingEventsRequestId);
        outState.putInt("popular_events_request_id", mPopularEventsRequestId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_event, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.item_search: {
                mSearchView.setIconified(false);
                return true;
            } default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onServiceCallback(int requestId, Intent requestIntent, int resultCode, Bundle data) {
        if (getNetworkServiceHelper().checkCommandClass(requestIntent, GetComingEventsCommand.class)) {
            ComingEventsFragment cef = getComingEventsFragment();
            if (resultCode == GetComingEventsCommand.RESPONSE_SUCCESS) {
                ArrayList<ComingEventItem> comingEvents = data.getParcelableArrayList("coming_events");
                cef.setListAdapter(new ComingEventsAdapter(this, comingEvents));
                cef.setListShown(true);
            } else if (resultCode == GetComingEventsCommand.RESPONSE_PROGRESS) {
                // TODO For the future
            } else if (resultCode == GetComingEventsCommand.RESPONSE_FAILURE) {
                cef.setListShown(true);
            }
        }

        if (getNetworkServiceHelper().checkCommandClass(requestIntent, GetPopularEventsCommand.class)) {
            PopularEventsFragment pef = getPopularEventsFragment();
            if (resultCode == GetPopularEventsCommand.RESPONSE_SUCCESS) {
                ArrayList<PopularEventItem> popularEvents = data.getParcelableArrayList("popular_events");
                pef.setListAdapter(new PopularEventsAdapter(this, popularEvents));
                pef.setListShown(true);
            } else if (resultCode == GetPopularEventsCommand.RESPONSE_PROGRESS) {
                // TODO For the future
            } else if (resultCode == GetPopularEventsCommand.RESPONSE_FAILURE) {
                pef.setListShown(true);
            }
        }
    }

    // TODO For card style in fragments?
//        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//                .getDisplayMetrics());
//        mPager.setPageMargin(pageMargin);

    // TODO This method will be used for setting custom color
//    private void changeColor(int newColor) {
//
//        tabs.setIndicatorColor(newColor);
//
//        // change ActionBar color just if an ActionBar is available
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//            Drawable colorDrawable = new ColorDrawable(newColor);
//            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
//            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });
//
//            if (oldBackground == null) {
//
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    ld.setCallback(drawableCallback);
//                } else {
//                    getActionBar().setBackgroundDrawable(ld);
//                }
//
//            } else {
//
//                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });
//
//                // workaround for broken ActionBarContainer drawable handling on
//                // pre-API 17 builds
//                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    td.setCallback(drawableCallback);
//                } else {
//                    getActionBar().setBackgroundDrawable(td);
//                }
//
//                td.startTransition(200);
//
//            }
//
//            oldBackground = ld;
//
//            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
//            getActionBar().setDisplayShowTitleEnabled(false);
//            getActionBar().setDisplayShowTitleEnabled(true);
//
//        }
//
//        currentColor = newColor;
//
//    }
}
