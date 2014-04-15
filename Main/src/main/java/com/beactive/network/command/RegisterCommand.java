package com.beactive.network.command;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.acbelter.nslib.command.BaseNetworkServiceCommand;

public class RegisterCommand extends BaseNetworkServiceCommand {
    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {

    }

    public static final Parcelable.Creator<RegisterCommand> CREATOR =
            new Parcelable.Creator<RegisterCommand>() {
                @Override
                public RegisterCommand createFromParcel(Parcel in) {
                    return new RegisterCommand();
                }

                @Override
                public RegisterCommand[] newArray(int size) {
                    return new RegisterCommand[size];
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
