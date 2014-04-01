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

public class GetScheduleCommand extends BaseNetworkServiceCommand {
    private static final String TAG = "beActive-network";
    private int mRootId;
    private String mDestinationsPath;

    public GetScheduleCommand(int rootId, String destinationsPath) {
        mRootId = rootId;
        mDestinationsPath = destinationsPath;
    }

    private GetScheduleCommand(Parcel in) {
        mRootId = in.readInt();
        mDestinationsPath = in.readString();
    }

    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // Download schedule from server in JSON format
        // FIXME Testing implementation
        try {
            networkSimulation();
            String json = Utils.readToString(context.getResources().openRawResource(R.raw.schedule));

            if (json != null) {
                try {
                    Bundle data = new Bundle();
                    data.putString("schedule_json", json);
                    data.putParcelableArrayList("schedule",
                            ResponseParser.parseScheduleFromJson(json));
                    notifySuccess(data);
                } catch (JSONException e) {
                    notifyFailure(null);
                }
            } else {
                notifyFailure(null);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open schedule from resources.");
        }
    }

    public static final Parcelable.Creator<GetScheduleCommand> CREATOR =
            new Parcelable.Creator<GetScheduleCommand>() {
                @Override
                public GetScheduleCommand createFromParcel(Parcel in) {
                    return new GetScheduleCommand(in);
                }

                @Override
                public GetScheduleCommand[] newArray(int size) {
                    return new GetScheduleCommand[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mRootId);
        out.writeString(mDestinationsPath);
    }
}
