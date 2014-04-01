package com.beactive.destination;

import android.os.Parcel;
import android.os.Parcelable;

public class DestinationRootItem implements Parcelable {
    protected int mId;
    protected String mTitle;

    public DestinationRootItem(int id, String title) {
        mId = id;
        mTitle = title;
    }

    private DestinationRootItem(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
    }

    public static final Parcelable.Creator<DestinationRootItem> CREATOR =
            new Parcelable.Creator<DestinationRootItem>() {
                @Override
                public DestinationRootItem createFromParcel(Parcel in) {
                    return new DestinationRootItem(in);
                }

                @Override
                public DestinationRootItem[] newArray(int size) {
                    return new DestinationRootItem[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mTitle);
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
