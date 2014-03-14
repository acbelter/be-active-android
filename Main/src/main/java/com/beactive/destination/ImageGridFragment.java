package com.beactive.destination;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.beactive.R;

import java.util.List;

public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private DestinationsTree.StructureLevel mLevel;
    private GridView mGridView;
    private ImageGridAdapter mAdapter;
    private OnDestinationsLevelListener mCallback;
    private List<DestinationItem> mItems;

    public static ImageGridFragment newInstance(DestinationsTree.StructureLevel level,
                                                List<DestinationItem> items,
                                                OnDestinationsLevelListener callback) {
        ImageGridFragment imageGridFragment = new ImageGridFragment();
        imageGridFragment.mLevel = level;
        imageGridFragment.mItems = items;
        imageGridFragment.mCallback = callback;
        imageGridFragment.setRetainInstance(true);
        return imageGridFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_grid, container, false);
        mGridView = (GridView) view.findViewById(R.id.image_grid_view);
        mGridView.setOnItemClickListener(this);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mLevel.title);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ImageGridAdapter(getActivity(), mItems);
        mGridView.setAdapter(mAdapter);
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
