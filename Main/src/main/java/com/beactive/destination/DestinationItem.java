package com.beactive.destination;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DestinationItem implements Parcelable {
    protected String mTitle;
    protected String mImageLink;
    protected Bitmap mImage;
    protected ArrayList<DestinationItem> mElements;

    public DestinationItem(String title) {
        mTitle = title;
        mElements = new ArrayList<DestinationItem>();
    }

    private DestinationItem(Parcel in) {
        mTitle = in.readString();
        mImageLink = in.readString();
        mImage = in.readParcelable(Bitmap.class.getClassLoader());
        mElements = new ArrayList<DestinationItem>();
        in.readTypedList(mElements, DestinationItem.CREATOR);
    }

    public static final Parcelable.Creator<DestinationItem> CREATOR =
            new Parcelable.Creator<DestinationItem>() {
                @Override
                public DestinationItem createFromParcel(Parcel in) {
                    return new DestinationItem(in);
                }

                @Override
                public DestinationItem[] newArray(int size) {
                    return new DestinationItem[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mImageLink);
        out.writeParcelable(mImage, flags);
        out.writeTypedList(mElements);
    }

    public void addElement(DestinationItem element) {
        mElements.add(element);
    }

    public void setElements(ArrayList<DestinationItem> elements) {
        if (elements != null) {
            mElements = elements;
        }
    }

    public List<DestinationItem> getElements() {
        return mElements;
    }

    public String getTitle() {
        return mTitle;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public void setImageLink(String mImageLink) {
        this.mImageLink = mImageLink;
    }
}
