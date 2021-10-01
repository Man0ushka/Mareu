package com.example.mareu.events;

public class SetMeetingTimeEvent {
    String tag;
    int hour;
    int minute;

    public SetMeetingTimeEvent(String tag, int hour, int minute) {
        this.tag = tag;
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTag() {
        return tag;
    }
}
