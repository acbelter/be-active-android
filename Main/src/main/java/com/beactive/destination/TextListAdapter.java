package com.beactive.destination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beactive.R;

import java.util.List;

public class TextListAdapter extends ArrayAdapter<DestinationItem> {
    private LayoutInflater mInflater;

    public TextListAdapter(Context context, List<DestinationItem> items) {
        super(context, R.layout.item_text_list, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_text_list, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DestinationItem item = getItem(position);
        holder.title.setText(item.getTitle());

        return convertView;
    }
}
