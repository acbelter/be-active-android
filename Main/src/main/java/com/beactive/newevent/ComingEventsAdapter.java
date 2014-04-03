package com.beactive.newevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beactive.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ComingEventsAdapter extends ArrayAdapter<ComingEventItem> {
    private ImageLoader mImageLoader;
    private AnimateFirstDisplayListener mAnimateFirstListener;
    private LayoutInflater mInflater;

    public ComingEventsAdapter(Context context, List<ComingEventItem> items) {
        super(context, R.layout.item_coming_event, items);
        mImageLoader = ImageLoader.getInstance();
        mAnimateFirstListener = new AnimateFirstDisplayListener();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        ImageView eventImage;
        TextView title;
        TextView owner;
        TextView datetimeDescription;
        TextView addEventLink;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_coming_event, null);

            holder = new ViewHolder();
            holder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.owner = (TextView) convertView.findViewById(R.id.owner);
            holder.datetimeDescription = (TextView) convertView.findViewById(R.id.datetime_description);
            holder.addEventLink = (TextView) convertView.findViewById(R.id.add_event_link);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ComingEventItem item = getItem(position);
        mImageLoader.displayImage(item.getImageLink(), holder.eventImage, mAnimateFirstListener);
        holder.title.setText(item.getTitle());
        holder.owner.setText(item.getOwner());
        holder.datetimeDescription.setText(item.makeDatetimeDescription());

        holder.addEventLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });

        return convertView;
    }
}
