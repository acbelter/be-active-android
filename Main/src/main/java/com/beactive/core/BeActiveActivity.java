package com.beactive.core;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.beactive.network.NetworkServiceCallbackListener;
import com.beactive.network.NetworkServiceHelper;

public abstract class BeActiveActivity extends ActionBarActivity implements NetworkServiceCallbackListener {
    private NetworkServiceHelper mServiceHelper;

    protected BeActiveApplication getApp() {
        return (BeActiveApplication) getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceHelper = getApp().getNetworkServiceHelper();
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

    public NetworkServiceHelper getNetworkServiceHelper() {
        return mServiceHelper;
    }
}
