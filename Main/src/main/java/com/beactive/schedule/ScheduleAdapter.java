package com.beactive.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beactive.core.BaseItem;

import java.util.ArrayList;
import java.util.Collections;

public class ScheduleAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    protected ArrayList<BaseItem> mItems;

    public ScheduleAdapter(Context context, ArrayList<BaseItem> items) throws InvalidScheduleException {
        super();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (items != null) {
            mItems = items;
        } else {
            mItems = new ArrayList<BaseItem>(0);
        }
        Collections.sort(mItems);
        if (!checkSchedule(mItems)) {
            throw new InvalidScheduleException("Invalid schedule.");
        }
    }

    private boolean checkSchedule(ArrayList<BaseItem> items) {
        if (items == null) {
            return true;
        }

        for (int i = 0; i < items.size() - 1; i++) {
            if (items.get(i).getEndTime() > items.get(i+1).getStartTime() ||
                    items.get(i).getEndTime() - items.get(i+1).getStartTime() > 24*60*60*10000) {
                return false;
            }
        }

        return true;
    }

    public void add(BaseItem item) {
        mItems.add(item);
    }

    public void removeForId(Long id) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getId() == id) {
                mItems.remove(i);
                i--;
            }
        }
    }

    public ArrayList<BaseItem> getItems() {
        return mItems;
    }

    @Override
    public int getViewTypeCount() {
        return ScheduleConstants.ITEM_TYPES_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof ScheduleItem) {
            return ScheduleConstants.ITEM_TYPE_REGULAR;
        }
        if (getItem(position) instanceof EventItem) {
            return ScheduleConstants.ITEM_TYPE_EVENT;
        }
        return -1;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public BaseItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }

    public static class InvalidScheduleException extends Exception {
        public InvalidScheduleException(String message) {
            super(message);
        }
    }
}
