package com.beactive.schedule;

public class ScheduleConstants {
    // TODO Move colors to resources
    static final int COLOR_LECTURE = 0xFFFF99CC;
    static final int COLOR_SEMINAR = 0xFFCCFFFF;
    // FIXME Change color name
    static final int COLOR_YELLOW = 0xFFFFFF99;
    static final int COLOR_LEISURE = 0xFFCCFFCC;

    static final long MIN_DURATION = 30*60*1000L; // 30 min in millis
    static final int MIN_HEIGHT = 60;

    static final int ITEM_TYPES_COUNT = 2;
    static final int ITEM_TYPE_REGULAR = 0;
    static final int ITEM_TYPE_EVENT = 1;
}
