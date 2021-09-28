package com.example.mareu.events;

import android.widget.DatePicker;

public class SetMeetingDateEvent {
    String tag;
    int year;
    int month;
    int dayOfMonth;
    public SetMeetingDateEvent(String tag, int year, int month, int dayOfMonth)
    {
        this.tag=tag;
        this.year=year;
        this.month=month;
        this.dayOfMonth=dayOfMonth;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getTag() {
        return tag;
    }
}
