package com.beactive.destination;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.beactive.R;

import java.util.LinkedHashMap;
import java.util.List;

// FIXME Use Singleton pattern for this class
public class TreeFragmentManager implements OnDestinationsLevelListener {
    private FragmentManager mFragmentManager;
    private DestinationsTree mTree;
    private int mTreeDepth;
    private int mCurrentDepth;
    // Map<type, DestinationItem>
    private LinkedHashMap<Integer, DestinationItem> mSelectedDestinations;
    private OnSelectionFinishedListener mCallback;
    private final int mContainerViewId;
    private DestinationItem mCurrentSelection;
    private boolean mSelectionStarted;

    public TreeFragmentManager(FragmentManager fragmentManager, int containerViewId, DestinationsTree tree,
                               OnSelectionFinishedListener selectionFinishedListener) {
        mFragmentManager = fragmentManager;
        mContainerViewId = containerViewId;
        mTree = tree;
        mCallback = selectionFinishedListener;
        mTreeDepth = tree.getStructure().size();
        mSelectedDestinations = new LinkedHashMap<Integer, DestinationItem>(mTreeDepth);
    }

    /**
     * @return True, if selection was started successfully, otherwise return false.
     */
    public boolean startSelection() {
        if (mTree.isEmpty() || mSelectionStarted) {
            return false;
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        DestinationsTree.StructureLevel level = mTree.getStructureLevel(mCurrentDepth);
        List<DestinationItem> root = mTree.getTree();
        Fragment f = getNewSelectFragment(level, root);

        ft.replace(mContainerViewId, f, Integer.toString(mCurrentDepth));
        ft.commit();

        mSelectionStarted = true;
        return true;
    }

    public boolean hasNextSelectFragment() {
        if (mCurrentSelection == null) {
            return mCurrentDepth < mTreeDepth - 1;
        } else {
            return mCurrentDepth < mTreeDepth - 1 && !mCurrentSelection.getElements().isEmpty();
        }
    }

    /**
     * @return True, if next fragment was shown successfully, otherwise return false.
     */
    public boolean showNextSelectFragment() {
        if (!mSelectionStarted) {
            throw new IllegalStateException("You should call startSelection() before using this method.");
        }

        if (hasNextSelectFragment()) {
            mCurrentDepth++;
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                    R.anim.slide_in_right, R.anim.slide_out_left);

            DestinationsTree.StructureLevel level = mTree.getStructureLevel(mCurrentDepth);
            List<DestinationItem> items = mCurrentSelection.getElements();
            Fragment f = getNewSelectFragment(level, items);

            ft.replace(mContainerViewId, f, Integer.toString(mCurrentDepth));
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        return false;
    }

    private Fragment getNewSelectFragment(DestinationsTree.StructureLevel level, List<DestinationItem> items) {
        switch (level.style) {
            case DestinationsTree.STYLE_IMAGE: {
                return ImageGridFragment.newInstance(level, items, this);
            }
            case DestinationsTree.STYLE_TEXT: {
                return TextListFragment.newInstance(level, items, this);
            }
            // TODO Add case DestinationsTree.STYLE_IMAGE_AND_TEXT in the future
            default: {
                throw new IllegalArgumentException("Unsupported style of level fragment: " + level.style);
            }
        }
    }

    @Override
    public void onDestinationSelected(int type, DestinationItem item) {
        mCurrentSelection = item;
        mSelectedDestinations.put(type, item);
        if (hasNextSelectFragment()) {
            showNextSelectFragment();
        } else {
            mCallback.onSelectionFinished(mSelectedDestinations);
        }
    }

    @Override
    public void onLevelCancelled(int type) {
        if (mCurrentDepth > 0) {
            mCurrentDepth--;
        }
        mSelectedDestinations.remove(type);
    }
}
