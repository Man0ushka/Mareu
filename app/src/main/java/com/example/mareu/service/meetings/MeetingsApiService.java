package com.example.mareu.service.meetings;

import com.example.mareu.model.Meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MeetingsApiService implements IMeetingsApiService {
    final List<Meeting> meetingList = MeetingsGenerator.generateMeetingsList();
    final List<Meeting> meetingDisplayedList = MeetingsGenerator.generateMeetingsList();
    String filterText;
    String filterType;

    @Override
    public List<Meeting> getMeetings() {
        return meetingList;
    }

    @Override
    public List<Meeting> getMeetingsDisplayed() {
        return meetingDisplayedList;
    }

    @Override
    public void filterMeetings(String filterText, String filterType) {
        this.filterText = filterText;
        this.filterType = filterType;
        meetingDisplayedList.clear();
        if (filterText.isEmpty()) {
            meetingDisplayedList.addAll(meetingList);
        } else {
            filterText = filterText.toLowerCase();
            if (filterType.equals("room"))
                filterRoom(filterText);
            else if (filterType.equals("date"))
                filterDate(filterText);
        }
    }

    public void filterRoom(String filterText) {
        for (Meeting item : meetingList) {
            if (item.getRoom().getName().toLowerCase().equals(filterText)) {
                meetingDisplayedList.add(item);
            }
        }
    }

    public void filterDate(String filterText) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);

        for (Meeting item : meetingList) {
            String itemStart = dateFormat.format(item.getDateStart().getTime());
            if (itemStart.equals(filterText)) {
                meetingDisplayedList.add(item);
            }
        }
    }


    @Override
    public void createMeeting(Meeting meeting) {

        meetingList.add(meeting);
        if (filterText == null || filterText.isEmpty()) {
            meetingDisplayedList.add(meeting);
        } else {
            if (filterType.equals("room")) {
                if (filterText.equals(meeting.getRoom().getName())) {
                    meetingDisplayedList.add(meeting);
                }
            } else if (filterType.equals("date")) {
                DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy",Locale.FRANCE);
                if (filterText.equals(dateFormat.format(meeting.getDateStart().getTime()))) {
                    meetingDisplayedList.add(meeting);
                }
            }
        }
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
        if (filterText == null || filterText.isEmpty()) {
            meetingDisplayedList.remove(meeting);
        } else {
            if (filterType.equals("room")) {
                if (filterText.equals(meeting.getRoom().getName())) {
                    meetingDisplayedList.remove(meeting);
                }
            } else if (filterType.equals("date")) {
                DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy",Locale.FRANCE);
                if (filterText.equals(dateFormat.format(meeting.getDateStart().getTime()))) {
                    meetingDisplayedList.remove(meeting);
                }
            }
        }
    }
}
