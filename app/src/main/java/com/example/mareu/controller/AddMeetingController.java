package com.example.mareu.controller;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.util.GregorianCalendar;
import java.util.List;

public class AddMeetingController {
    List<Meeting> meetingList;

    public AddMeetingController(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public void checkForAvailableRooms(Meeting meeting, List<Room> roomList) {
        for (Meeting meetingInList : meetingList) {
            if (roomList.contains(meetingInList.getRoom())) {
                if (meetingIsDuringAnotherMeeting(meeting, meetingInList)) {
                    roomList.remove(meetingInList.getRoom());
                }
            }
        }
    }

    public Boolean meetingIsDuringAnotherMeeting(Meeting meeting, Meeting meetingInList) {
        GregorianCalendar startMeeting = meeting.getDateStart();
        GregorianCalendar endMeeting = meeting.getDateEnd();
        GregorianCalendar startMeetingInList = meetingInList.getDateStart();
        GregorianCalendar endMeetingInList = meetingInList.getDateEnd();

        if (dateIsBetweenTwoDates(startMeeting, startMeetingInList, endMeetingInList))
            return true;
        else return dateIsBetweenTwoDates(endMeeting, startMeetingInList, endMeetingInList);
    }

    public Boolean dateIsBetweenTwoDates(GregorianCalendar date, GregorianCalendar startDate, GregorianCalendar endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) < 0;
    }
}
