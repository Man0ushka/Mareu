package com.example.mareu.service.meetings;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface IMeetingsApiService {

    List<Meeting> getMeetings();
    List<Meeting> getMeetingsDisplayed();
    void createMeeting(Meeting meeting);
    void deleteMeeting(Meeting meeting);
    void filterMeetings(String text, String filterType);
}
