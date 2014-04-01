package com.beactive.destination;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DestinationsTree implements Parcelable {
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

    public static class StructureLevel implements Parcelable {
        public int type;
        public String title;
        public int style;

        public StructureLevel(int type, String title, int style) {
            this.type = type;
            this.title = title;
            this.style = style;
        }

        private StructureLevel(Parcel in) {
            type = in.readInt();
            title = in.readString();
            style = in.readInt();
        }

        public static final Parcelable.Creator<StructureLevel> CREATOR =
                new Parcelable.Creator<StructureLevel>() {
            @Override
            public StructureLevel createFromParcel(Parcel in) {
                return new StructureLevel(in);
            }

            @Override
            public StructureLevel[] newArray(int size) {
                return new StructureLevel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(type);
            out.writeString(title);
            out.writeInt(style);
        }
    }

    public DestinationsTree(int rootId) {
        mRootId = rootId;
        mStructure = new ArrayList<StructureLevel>();
        mTree = new ArrayList<DestinationItem>();
    }

    private DestinationsTree(Parcel in) {
        mRootId = in.readInt();
        mStructure = new ArrayList<StructureLevel>();
        in.readTypedList(mStructure, StructureLevel.CREATOR);
        mTree = new ArrayList<DestinationItem>();
        in.readTypedList(mTree, DestinationItem.CREATOR);
    }

    public static final Parcelable.Creator<DestinationsTree> CREATOR =
            new Parcelable.Creator<DestinationsTree>() {
                @Override
                public DestinationsTree createFromParcel(Parcel in) {
                    return new DestinationsTree(in);
                }

                @Override
                public DestinationsTree[] newArray(int size) {
                    return new DestinationsTree[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mRootId);
        out.writeTypedList(mStructure);
        out.writeTypedList(mTree);
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
