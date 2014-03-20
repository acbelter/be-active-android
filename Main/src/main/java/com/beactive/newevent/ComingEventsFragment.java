package com.beactive.newevent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.beactive.R;
import com.beactive.network.ServerConnection;

public class ComingEventsFragment extends ListFragment {
    private ServerConnection mServerConnection;
    private ComingEventsAdapter mAdapter;
    private Button mMoreButton;

    public static ComingEventsFragment newInstance(ServerConnection conn) {
        ComingEventsFragment comingEventsFragment = new ComingEventsFragment();
        comingEventsFragment.mServerConnection = conn;
        return comingEventsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coming_events, container, false);
        mMoreButton = (Button) view.findViewById(R.id.more_button);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ComingEventsAdapter(getActivity(), mServerConnection.getComingEvents());
        setListAdapter(mAdapter);
    }
}