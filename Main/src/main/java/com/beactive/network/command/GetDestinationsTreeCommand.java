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

public class GetDestinationsTreeCommand extends BaseNetworkServiceCommand {
    private static final String TAG = "beActive-network";
    private int mRootId;

    public GetDestinationsTreeCommand(int rootId) {
        mRootId = rootId;
    }

    private GetDestinationsTreeCommand(Parcel in) {
        mRootId = in.readInt();
    }

    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // Download destinations tree from server in JSON format
        // FIXME Testing implementation
        try {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String json = Utils.readToString(context.getResources().openRawResource(R.raw.destinations));

            if (json != null) {
                try {
                    Bundle data = new Bundle();
                    data.putParcelable("destinations_tree",
                            ResponseParser.parseDestinationsTreeFromJson(json));
                    notifySuccess(data);
                } catch (JSONException e) {
                    notifyFailure(null);
                }
            } else {
                notifyFailure(null);
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open destinations tree from resources.");
        }
    }

    public static final Parcelable.Creator<GetDestinationsTreeCommand> CREATOR =
            new Parcelable.Creator<GetDestinationsTreeCommand>() {
                @Override
                public GetDestinationsTreeCommand createFromParcel(Parcel in) {
                    return new GetDestinationsTreeCommand(in);
                }

                @Override
                public GetDestinationsTreeCommand[] newArray(int size) {
                    return new GetDestinationsTreeCommand[size];
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
