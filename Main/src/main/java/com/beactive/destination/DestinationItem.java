package com.beactive.destination;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class DestinationItem {
    protected String mTitle;
    protected String mImageLink;
    protected Bitmap mImage;
    protected List<DestinationItem> mElements;

    public DestinationItem(String title) {
        mTitle = title;
        mElements = new ArrayList<DestinationItem>();
    }

    public void addElement(DestinationItem element) {
        mElements.add(element);
    }

    public void setElements(List<DestinationItem> elements) {
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
