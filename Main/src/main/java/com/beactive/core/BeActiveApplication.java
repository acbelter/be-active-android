package com.beactive.core;

import android.app.Application;
import android.content.Context;

import com.beactive.network.NetworkServiceHelper;

public class BeActiveApplication extends Application {
    public static final String PACKAGE = "com.beactive";

    private NetworkServiceHelper mServiceHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mServiceHelper = new NetworkServiceHelper(this);
    }

    public NetworkServiceHelper getNetworkServiceHelper() {
        return mServiceHelper;
    }

    public static BeActiveApplication getApplication(Context context) {
        if (context instanceof BeActiveApplication) {
            return (BeActiveApplication) context;
        }
        return (BeActiveApplication) context.getApplicationContext();
    }
}
