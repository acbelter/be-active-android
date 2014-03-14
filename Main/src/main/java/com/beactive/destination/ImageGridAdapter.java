package com.beactive.destination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.beactive.R;

import java.util.List;

public class ImageGridAdapter extends ArrayAdapter<DestinationItem> {
    private LayoutInflater mInflater;

    public ImageGridAdapter(Context context, List<DestinationItem> objects) {
        super(context, R.layout.item_image_grid, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        ImageView gridImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_image_grid, null);

            ViewHolder holder = new ViewHolder();
            holder.gridImage = (ImageView) convertView.findViewById(R.id.image_grid);
            convertView.setTag(holder);
        }

        DestinationItem item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (item.getImage() != null) {
            holder.gridImage.setImageBitmap(item.getImage());
        }

        return convertView;
    }
}
