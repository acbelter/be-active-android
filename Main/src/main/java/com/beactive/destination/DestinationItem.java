package com.beactive.destination;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class DestinationItem {
    private String mTitle;
    private String mImageLink;
    private Drawable mImage;
    private List<DestinationItem> mElements;

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

    public String getTitle() {
        return mTitle;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable mImage) {
        this.mImage = mImage;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public void setImageLink(String mImageLink) {
        this.mImageLink = mImageLink;
    }
}
