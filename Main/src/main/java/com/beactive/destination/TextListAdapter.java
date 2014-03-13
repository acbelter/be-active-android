package com.beactive.destination;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.beactive.R;

import java.util.List;

public class TextListAdapter extends ArrayAdapter<DestinationItem> {
    public TextListAdapter(Context context, List<DestinationItem> objects) {
        super(context, R.layout.item_text_list, objects);
    }
}
