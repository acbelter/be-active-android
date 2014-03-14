package com.beactive.destination;

public interface OnDestinationsLevelListener {
    void onDestinationSelected(int type, DestinationItem item);
    void onLevelCancelled(int type);
}
