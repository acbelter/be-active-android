package com.beactive.newevent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.beactive.R;

public class PopularEventsFragment extends ListFragment {
    private Button mMoreButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_events, container, false);
        mMoreButton = (Button) view.findViewById(R.id.more_button);
        // FIXME For testing
        mMoreButton.setEnabled(false);
        return view;
    }
}
