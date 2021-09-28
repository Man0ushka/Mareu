package com.example.mareu.events;

import android.view.View;

import com.example.mareu.model.Meeting;

import java.util.List;

public class DeleteMeetingEvent {
    Meeting meeting;
    View view;

    public DeleteMeetingEvent(Meeting meeting, View view)
    {
        this.meeting=meeting;
        this.view=view;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public View getView() {
        return view;
    }
}
