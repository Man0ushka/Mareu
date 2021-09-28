package com.example.mareu.controller;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddMeetingController {
    List<Meeting> meetingList;
    public AddMeetingController(List<Meeting> meetingList)
    {
        this.meetingList=meetingList;
    }

    public void checkForAvailableRooms(Meeting meeting, List<Room> roomList)
    {


        for(Meeting meetingInList:meetingList)
        {
            if(roomList.contains(meetingInList.getRoom()))
            {
                if (meetingIsDuringAnotherMeeting(meeting, meetingInList))
                {

                    roomList.remove(meetingInList.getRoom());
                }
            }

        }
        return;
    }
    public Boolean meetingIsDuringAnotherMeeting(Meeting meeting, Meeting meetingInList)
    {
        GregorianCalendar startMeeting=meeting.getDateStart();
        GregorianCalendar endMeeting=meeting.getDateEnd();
        GregorianCalendar startMeetingInList=meetingInList.getDateStart();
        GregorianCalendar endMeetingInList=meetingInList.getDateEnd();

        if(dateIsBetweenTwoDates(startMeeting,startMeetingInList,endMeetingInList))
            return true;
        else if(dateIsBetweenTwoDates(endMeeting,startMeetingInList,endMeetingInList))
            return true;
        else return false;
    }
    public Boolean dateIsBetweenTwoDates(GregorianCalendar date,GregorianCalendar startDate, GregorianCalendar endDate)
    {
        if(date.compareTo(startDate)>=0 && date.compareTo(endDate)<0)
            return true;
        else return false;
    }
}
