package com.beactive.core;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.acbelter.nslib.NetworkServiceCallbackListener;
import com.beactive.network.BeActiveNetworkServiceHelper;

public abstract class BeActiveActivity extends ActionBarActivity
        implements NetworkServiceCallbackListener {
    private BeActiveNetworkServiceHelper mServiceHelper;

    protected BeActiveApplication getApp() {
        return (BeActiveApplication) getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceHelper = new BeActiveNetworkServiceHelper(getApp().getNetworkServiceHelper());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mServiceHelper.addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mServiceHelper.removeListener(this);
    }

    public BeActiveNetworkServiceHelper getNetworkServiceHelper() {
        return mServiceHelper;
    }
}
