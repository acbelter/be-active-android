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

public class GetPopularEventsCommand extends BaseNetworkServiceCommand {
    private static final String TAG = "beActive-network";
    private int mRootId;

    public GetPopularEventsCommand(int rootId) {
        mRootId = rootId;
    }

    private GetPopularEventsCommand(Parcel in) {
        mRootId = in.readInt();
    }

    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // Download popular events from server in JSON format
        // FIXME Testing implementation
        try {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String json = Utils.readToString(context.getResources().openRawResource(R.raw.popular_events));

            if (json != null) {
                try {
                    Bundle data = new Bundle();
                    data.putParcelableArrayList("popular_events",
                            ResponseParser.parsePopularEventsFromJson(json));
                    notifySuccess(data);
                } catch (JSONException e) {
                    notifyFailure(null);
                }
            } else {
                notifyFailure(null);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open popular events from resources.");
        }
    }

    public static final Parcelable.Creator<GetPopularEventsCommand> CREATOR =
            new Parcelable.Creator<GetPopularEventsCommand>() {
                @Override
                public GetPopularEventsCommand createFromParcel(Parcel in) {
                    return new GetPopularEventsCommand(in);
                }

                @Override
                public GetPopularEventsCommand[] newArray(int size) {
                    return new GetPopularEventsCommand[size];
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
