package com.beactive.destination;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.beactive.R;

import java.util.List;

public class TextListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private DestinationsTree.StructureLevel mLevel;
    private ListView mListView;
    private TextListAdapter mAdapter;
    private OnDestinationsLevelListener mCallback;
    private List<DestinationItem> mItems;

    public static TextListFragment newInstance(DestinationsTree.StructureLevel level,
                                               List<DestinationItem> items,
                                               OnDestinationsLevelListener callback) {
        TextListFragment textListFragment = new TextListFragment();
        textListFragment.mLevel = level;
        textListFragment.mItems = items;
        textListFragment.mCallback = callback;
        textListFragment.setRetainInstance(true);
        return textListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_list, container, false);
        mListView = (ListView) view.findViewById(R.id.text_list_view);
        mListView.setOnItemClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mLevel.title);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new TextListAdapter(getActivity(), mItems);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback.onLevelCancelled(mLevel.type);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCallback.onDestinationSelected(mLevel.type, mAdapter.getItem(position));
    }
}
