package com.beactive.network;

import android.content.Intent;

import com.acbelter.nslib.BaseNetworkServiceHelper;
import com.acbelter.nslib.NetworkServiceCallbackListener;
import com.acbelter.nslib.NetworkServiceHelper;
import com.acbelter.nslib.command.BaseNetworkServiceCommand;
import com.beactive.network.command.GetComingEventsCommand;
import com.beactive.network.command.GetDestinationsRootCommand;
import com.beactive.network.command.GetDestinationsTreeCommand;
import com.beactive.network.command.GetEventsCommand;
import com.beactive.network.command.GetPopularEventsCommand;
import com.beactive.network.command.GetScheduleCommand;

public class BeActiveNetworkServiceHelper implements NetworkServiceHelper {
    private BaseNetworkServiceHelper mBaseNetworkServiceHelper;

    public BeActiveNetworkServiceHelper(BaseNetworkServiceHelper baseNetworkServiceHelper) {
        mBaseNetworkServiceHelper = baseNetworkServiceHelper;
    }

    public int getDestinationsRoot() {
        final int requestId = mBaseNetworkServiceHelper.createCommandId();

        Intent requestIntent = mBaseNetworkServiceHelper.buildRequestIntent(new GetDestinationsRootCommand(), requestId);
        return mBaseNetworkServiceHelper.executeRequest(requestId, requestIntent);
    }

    public int getDestinationsTree(int rootId) {
        final int requestId = mBaseNetworkServiceHelper.createCommandId();

        Intent requestIntent = mBaseNetworkServiceHelper.buildRequestIntent(new GetDestinationsTreeCommand(rootId), requestId);
        return mBaseNetworkServiceHelper.executeRequest(requestId, requestIntent);
    }

    public int getSchedule(int rootId, String destinationsPath) {
        final int requestId = mBaseNetworkServiceHelper.createCommandId();

        Intent requestIntent = mBaseNetworkServiceHelper.buildRequestIntent(new GetScheduleCommand(rootId, destinationsPath), requestId);
        return mBaseNetworkServiceHelper.executeRequest(requestId, requestIntent);
    }

    public int getEvents(int rootId) {
        final int requestId = mBaseNetworkServiceHelper.createCommandId();

        Intent requestIntent = mBaseNetworkServiceHelper.buildRequestIntent(new GetEventsCommand(rootId), requestId);
        return mBaseNetworkServiceHelper.executeRequest(requestId, requestIntent);
    }

    public int getComingEvents(int rootId) {
        final int requestId = mBaseNetworkServiceHelper.createCommandId();

        Intent requestIntent = mBaseNetworkServiceHelper.buildRequestIntent(new GetComingEventsCommand(rootId), requestId);
        return mBaseNetworkServiceHelper.executeRequest(requestId, requestIntent);
    }

    public int getPopularEvents(int rootId) {
        final int requestId = mBaseNetworkServiceHelper.createCommandId();

        Intent requestIntent = mBaseNetworkServiceHelper.buildRequestIntent(new GetPopularEventsCommand(rootId), requestId);
        return mBaseNetworkServiceHelper.executeRequest(requestId, requestIntent);
    }

    @Override
    public void addListener(NetworkServiceCallbackListener callback) {
        mBaseNetworkServiceHelper.addListener(callback);
    }

    @Override
    public void removeListener(NetworkServiceCallbackListener callback) {
        mBaseNetworkServiceHelper.removeListener(callback);
    }

    @Override
    public void cancelRequest(int requestId) {
        mBaseNetworkServiceHelper.cancelRequest(requestId);
    }

    @Override
    public boolean checkCommandClass(Intent requestIntent, Class<? extends BaseNetworkServiceCommand> commandClass) {
        return mBaseNetworkServiceHelper.checkCommandClass(requestIntent, commandClass);
    }

    @Override
    public boolean isPending(int requestId) {
        return mBaseNetworkServiceHelper.isPending(requestId);
    }
}
