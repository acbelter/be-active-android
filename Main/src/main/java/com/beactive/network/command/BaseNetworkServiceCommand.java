package com.beactive.network.command;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.beactive.core.BeActiveApplication;

import java.util.concurrent.TimeUnit;

public abstract class BaseNetworkServiceCommand implements Parcelable {
    public static String EXTRA_PROGRESS = BeActiveApplication.PACKAGE + ".network.EXTRA_PROGRESS";

    public static final int RESPONSE_SUCCESS = 0;
    public static final int RESPONSE_FAILURE = 1;
    public static final int RESPONSE_PROGRESS = 2;

    protected volatile boolean mCancelled;
    private ResultReceiver mCallback;

    public final void execute(Context context, Intent requestIntent, ResultReceiver callback) {
        mCallback = callback;
        doExecute(context, requestIntent, callback);
    }

    protected abstract void doExecute(Context context, Intent requestIntent, ResultReceiver callback);

    protected void notifySuccess(Bundle data) {
        sendUpdate(RESPONSE_SUCCESS, data);
    }

    protected void notifyFailure(Bundle data) {
        sendUpdate(RESPONSE_FAILURE, data);
    }

    protected void sendProgress(int progress) {
        Bundle data = new Bundle();
        data.putInt(EXTRA_PROGRESS, progress);
        sendUpdate(RESPONSE_PROGRESS, data);
    }

    private void sendUpdate(int resultCode, Bundle data) {
        if (mCallback != null) {
            mCallback.send(resultCode, data);
        }
    }

    public synchronized void cancel() {
        mCancelled = true;
    }

    // FIXME Just for testing!
    public void networkSimulation() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
