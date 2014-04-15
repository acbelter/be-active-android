package com.beactive.network.command;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.acbelter.nslib.command.BaseNetworkServiceCommand;

public class LoginCommand extends BaseNetworkServiceCommand {
    private String mEmail;
    private String mPassword;
    
    @Override
    protected void doExecute(Context context, Intent requestIntent, ResultReceiver callback) {
        // TODO Use HTTPS!

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<LoginCommand> CREATOR =
            new Parcelable.Creator<LoginCommand>() {
                @Override
                public LoginCommand createFromParcel(Parcel in) {
                    return new LoginCommand();
                }

                @Override
                public LoginCommand[] newArray(int size) {
                    return new LoginCommand[size];
                }
            };

    @Override
    public void writeToParcel(Parcel out, int flags) {

    }
}
