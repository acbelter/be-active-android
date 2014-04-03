package com.beactive.core;

import android.app.Application;
import android.content.Context;

import com.beactive.R;
import com.beactive.network.NetworkServiceHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BeActiveApplication extends Application {
    public static final String PACKAGE = "com.beactive";

    private NetworkServiceHelper mServiceHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mServiceHelper = new NetworkServiceHelper(this);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.default_event_icon)
                .showImageForEmptyUri(R.drawable.default_event_icon)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
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
