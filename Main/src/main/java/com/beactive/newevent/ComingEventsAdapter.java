package com.beactive.newevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beactive.R;

import java.util.List;

public class ComingEventsAdapter extends ArrayAdapter<ComingEventItem> {
    private LayoutInflater mInflater;

    public ComingEventsAdapter(Context context, List<ComingEventItem> items) {
        super(context, R.layout.item_coming_event, items);
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_coming_event, null);

            ViewHolder holder = new ViewHolder();
            holder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.owner = (TextView) convertView.findViewById(R.id.owner);
            holder.datetimeDescription = (TextView) convertView.findViewById(R.id.datetime_description);
            holder.addEventLink = (TextView) convertView.findViewById(R.id.add_event_link);
            convertView.setTag(holder);
        }

        ComingEventItem item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (item.getEventImage() != null) {
            holder.eventImage.setImageBitmap(item.getEventImage());
        }
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
