package com.beactive.network;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.SparseArray;

import com.beactive.network.command.BaseNetworkServiceCommand;
import com.beactive.network.command.GetComingEventsCommand;
import com.beactive.network.command.GetDestinationsRootCommand;
import com.beactive.network.command.GetDestinationsTreeCommand;
import com.beactive.network.command.GetEventsCommand;
import com.beactive.network.command.GetPopularEventsCommand;
import com.beactive.network.command.GetScheduleCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkServiceHelper {
    private List<NetworkServiceCallbackListener> mCallbacks;
    private AtomicInteger mRequestCounter;
    private SparseArray<Intent> mPendingRequests;
    private Application mApplication;

    public NetworkServiceHelper(Application application) {
        mApplication = application;
        mCallbacks = new ArrayList<NetworkServiceCallbackListener>();
        mRequestCounter = new AtomicInteger();
        mPendingRequests = new SparseArray<Intent>();
    }


    public int getDestinationsRoot() {
        final int requestId = createCommandId();

        Intent requestIntent = buildRequestIntent(new GetDestinationsRootCommand(), requestId);
        return executeRequest(requestId, requestIntent);
    }

    public int getDestinationsTree(int rootId) {
        final int requestId = createCommandId();

        Intent requestIntent = buildRequestIntent(new GetDestinationsTreeCommand(rootId), requestId);
        return executeRequest(requestId, requestIntent);
    }

    public int getSchedule(int rootId, String destinationsPath) {
        final int requestId = createCommandId();

        Intent requestIntent = buildRequestIntent(new GetScheduleCommand(rootId, destinationsPath), requestId);
        return executeRequest(requestId, requestIntent);
    }

    public int getEvents(int rootId) {
        final int requestId = createCommandId();

        Intent requestIntent = buildRequestIntent(new GetEventsCommand(rootId), requestId);
        return executeRequest(requestId, requestIntent);
    }

    public int getComingEvents(int rootId) {
        final int requestId = createCommandId();

        Intent requestIntent = buildRequestIntent(new GetComingEventsCommand(rootId), requestId);
        return executeRequest(requestId, requestIntent);
    }

    public int getPopularEvents(int rootId) {
        final int requestId = createCommandId();

        Intent requestIntent = buildRequestIntent(new GetPopularEventsCommand(rootId), requestId);
        return executeRequest(requestId, requestIntent);
    }


    public void addListener(NetworkServiceCallbackListener callback) {
        mCallbacks.add(callback);
    }

    public void removeListener(NetworkServiceCallbackListener callback) {
        mCallbacks.remove(callback);
    }

    public void cancelRequest(int requestId) {
        Intent cancelIntent = new Intent(mApplication, NetworkService.class);
        cancelIntent.setAction(NetworkService.ACTION_CANCEL_COMMAND);
        cancelIntent.putExtra(NetworkService.EXTRA_REQUEST_ID, requestId);

        mApplication.startService(cancelIntent);
        mPendingRequests.remove(requestId);
    }

    public boolean checkCommandClass(Intent requestIntent,
                                     Class<? extends BaseNetworkServiceCommand> commandClass) {
        Parcelable commandExtra = requestIntent.getParcelableExtra(NetworkService.EXTRA_COMMAND);
        return commandExtra != null && commandExtra.getClass().equals(commandClass);
    }

    public boolean isPending(int requestId) {
        return mPendingRequests.get(requestId) != null;
    }

    private int createCommandId() {
        return mRequestCounter.getAndIncrement();
    }

    private int executeRequest(int requestId, Intent requestIntent) {
        mPendingRequests.append(requestId, requestIntent);
        mApplication.startService(requestIntent);
        return requestId;
    }

    private Intent buildRequestIntent(BaseNetworkServiceCommand command, final int requestId) {
        Intent requestIntent = new Intent(mApplication, NetworkService.class);
        requestIntent.setAction(NetworkService.ACTION_EXECUTE_COMMAND);

        requestIntent.putExtra(NetworkService.EXTRA_COMMAND, command);
        requestIntent.putExtra(NetworkService.EXTRA_REQUEST_ID, requestId);
        requestIntent.putExtra(NetworkService.EXTRA_RESULT_RECEIVER, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (isPending(requestId)) {
                    Intent pendingRequest = mPendingRequests.get(requestId);

                    for (int i = 0; i < mCallbacks.size(); i++) {
                        if (mCallbacks.get(i) != null) {
                            mCallbacks.get(i).onServiceCallback(requestId, pendingRequest, resultCode, resultData);
                        }
                    }

                    if (resultCode != BaseNetworkServiceCommand.RESPONSE_PROGRESS) {
                        mPendingRequests.remove(requestId);
                    }
                }
            }
        });
        return requestIntent;
    }
}
