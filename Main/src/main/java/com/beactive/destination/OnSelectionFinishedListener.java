package com.beactive.destination;

import java.util.LinkedHashMap;

public interface OnSelectionFinishedListener {
    void onSelectionFinished(LinkedHashMap<Integer, DestinationItem> selectionResult);
}
