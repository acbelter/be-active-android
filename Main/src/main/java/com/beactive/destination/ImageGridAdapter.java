package com.beactive.destination;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.beactive.R;

import java.util.List;

public class ImageGridAdapter extends ArrayAdapter<DestinationItem> {
    public ImageGridAdapter(Context context, List<DestinationItem> objects) {
        super(context, R.layout.item_image_grid, objects);
    }
}
