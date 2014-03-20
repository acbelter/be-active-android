package com.beactive.newevent;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.beactive.R;

import java.util.List;

public class PopularEventsAdapter extends ArrayAdapter<PopularEventItem> {
    public PopularEventsAdapter(Context context, List<PopularEventItem> items) {
        super(context, R.layout.item_popular_event, items);
    }
}
