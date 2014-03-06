package com.beactive;

public class ScheduleItem {
    public String startTime;
    public String endTime;
    public String title;

    public ScheduleItem(String startTime, String endTime, String title) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }
}
