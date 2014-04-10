package com.beactive.network.command;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import com.acbelter.nslib.command.BaseNetworkServiceCommand;
import com.beactive.R;
import com.beactive.network.ResponseParser;
import com.beactive.util.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GetEventsCommand extends BaseNetworkServiceCommand {
    private static final String TAG = "beActive-network";
    private int mRootId;

    public GetEventsCommand(int rootId) {
        mRootId = rootId;
    }

    private GetEventsCommand(Parcel in) {
        mRootId = in.readInt();
    }

    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // Download events from server in JSON format
        // FIXME Testing implementation
        try {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String json = Utils.readToString(context.getResources().openRawResource(R.raw.events));

            if (json != null) {
                try {
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("events",
                            ResponseParser.parseEventsFromJson(json));
                    notifySuccess(data);
                } catch (JSONException e) {
                    notifyFailure(null);
                }
            } else {
                notifyFailure(null);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open events from resources.");
        }
    }

    public static final Parcelable.Creator<GetEventsCommand> CREATOR =
            new Parcelable.Creator<GetEventsCommand>() {
                @Override
                public GetEventsCommand createFromParcel(Parcel in) {
                    return new GetEventsCommand(in);
                }

                @Override
                public GetEventsCommand[] newArray(int size) {
                    return new GetEventsCommand[size];
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
