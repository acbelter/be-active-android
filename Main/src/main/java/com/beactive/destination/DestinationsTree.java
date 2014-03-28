package com.beactive.destination;

import java.util.ArrayList;
import java.util.List;

public class DestinationsTree {
    public static final int TYPE_FACULTIES = 0;
    public static final int TYPE_COURSES = 1;
    public static final int TYPE_GROUPS = 2;
    public static final int TYPE_DEPARTMENTS = 3;

    public static final int STYLE_IMAGE = 0;
    public static final int STYLE_TEXT = 1;
    // TODO For the future
    public static final int STYLE_IMAGE_AND_TEXT = 2;

    private int mRootId;
    private List<StructureLevel> mStructure;
    private List<DestinationItem> mTree;

    public static class StructureLevel {
        public int type;
        public String title;
        public int style;

        public StructureLevel(int type, String title, int style) {
            this.type = type;
            this.title = title;
            this.style = style;
        }
    }

    public DestinationsTree(int rootId) {
        mRootId = rootId;
        mStructure = new ArrayList<StructureLevel>();
        mTree = new ArrayList<DestinationItem>();
    }

    public void addStructureLevel(StructureLevel level) {
        mStructure.add(level);
    }

    public StructureLevel getStructureLevel(int location) {
        return mStructure.get(location);
    }

    public List<StructureLevel> getStructure() {
        return mStructure;
    }

    public boolean isEmpty() {
        return mStructure.isEmpty();
    }

    public List<DestinationItem> getTree() {
        return mTree;
    }

    public void setTree(List<DestinationItem> tree) {
        mTree = tree;
    }

    public static String getTypeDescription(int type) {
        switch (type) {
            case TYPE_FACULTIES: {
                return "faculties";
            }
            case TYPE_COURSES: {
                return "courses";
            }
            case TYPE_GROUPS: {
                return "groups";
            }
            case TYPE_DEPARTMENTS: {
                return "departments";
            }
            default: {
                throw new IllegalArgumentException("Unsupported level type: " + type);
            }
        }
    }
}
