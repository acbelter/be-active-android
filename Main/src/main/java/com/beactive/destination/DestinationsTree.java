package com.beactive.destination;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DestinationsTree {
    public static final int STYLE_IMAGE = 0;
    public static final int STYLE_TEXT = 1;
    // TODO For the future
    public static final int STYLE_IMAGE_AND_TEXT = 2;

    private String mTreeTitle;
    private List<Pair<DestinationType, Integer>> mStructure;
    private List<DestinationItem> mTree;

    public enum DestinationType {
        faculties,
        courses,
        groups,
        departments
    }

    public DestinationsTree(String treeTitle) {
        mTreeTitle = treeTitle;
        mStructure = new ArrayList<Pair<DestinationType, Integer>>(DestinationType.values().length);
        mTree = new ArrayList<DestinationItem>();
    }

    public void addStructureLevel(Pair<DestinationType, Integer> level) {
        mStructure.add(level);
    }

    public List<Pair<DestinationType, Integer>> getStructure() {
        return mStructure;
    }

    public List<DestinationItem> getTree() {
        return mTree;
    }

    public void setTree(List<DestinationItem> tree) {
        mTree = tree;
    }

    public String getTreeTitle() {
        return mTreeTitle;
    }
}
