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

public class GetComingEventsCommand extends BaseNetworkServiceCommand {
    private static final String TAG = "beActive-network";
    private int mRootId;

    public GetComingEventsCommand(int rootId) {
        mRootId = rootId;
    }

    private GetComingEventsCommand(Parcel in) {
        mRootId = in.readInt();
    }

    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // Download coming events from server in JSON format
        // FIXME Testing implementation
        try {
            networkSimulation();
            String json = Utils.readToString(context.getResources().openRawResource(R.raw.coming_events));

            if (json != null) {
                try {
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("coming_events",
                            ResponseParser.parseComingEventsFromJson(json));
                    notifySuccess(data);
                } catch (JSONException e) {
                    notifyFailure(null);
                }
            } else {
                notifyFailure(null);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open coming events from resources.");
        }
    }

    public static final Parcelable.Creator<GetComingEventsCommand> CREATOR =
            new Parcelable.Creator<GetComingEventsCommand>() {
                @Override
                public GetComingEventsCommand createFromParcel(Parcel in) {
                    return new GetComingEventsCommand(in);
                }

                @Override
                public GetComingEventsCommand[] newArray(int size) {
                    return new GetComingEventsCommand[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mRootId);
    }
}
