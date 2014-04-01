package com.beactive.network.command;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import com.beactive.R;
import com.beactive.network.ResponseParser;
import com.beactive.util.Utils;

import org.json.JSONException;

import java.io.IOException;

public class GetDestinationsRootCommand extends BaseNetworkServiceCommand {
    private static final String TAG = "beActive-network";

    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // Download root of destinations tree from server in JSON format
        // FIXME Testing implementation
        try {
            networkSimulation();
            String json = Utils.readToString(context.getResources().openRawResource(R.raw.destinations_root));

            if (json != null) {
                try {
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("destinations_root",
                            ResponseParser.parseDestinationsRootFromJson(json));
                    notifySuccess(data);
                } catch (JSONException e) {
                    notifyFailure(null);
                }
            } else {
                notifyFailure(null);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open destinations root from resources.");
        }
    }

    public static final Parcelable.Creator<GetDestinationsRootCommand> CREATOR =
            new Parcelable.Creator<GetDestinationsRootCommand>() {
                @Override
                public GetDestinationsRootCommand createFromParcel(Parcel in) {
                    return new GetDestinationsRootCommand();
                }

                @Override
                public GetDestinationsRootCommand[] newArray(int size) {
                    return new GetDestinationsRootCommand[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

    }
}
