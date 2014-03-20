package com.beactive.newevent;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.beactive.R;
import com.beactive.network.ConnectionMock;
import com.beactive.network.ServerConnection;

public class NewEventActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {
    private ServerConnection mServerConnection;
    private SearchView mSearchView;
    private NewEventPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mServerConnection = new ConnectionMock(this);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        mAdapter = new NewEventPagerAdapter(getResources(), getSupportFragmentManager(), mServerConnection);

        pager.setAdapter(mAdapter);
        tabs.setViewPager(pager);
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
