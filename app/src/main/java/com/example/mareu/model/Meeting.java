package com.example.mareu.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Meeting {
    Room room;
    User meetingCreator;
    List<User> participants;
    GregorianCalendar dateStart;
    GregorianCalendar dateEnd;
    String meetingSubject;

    public Meeting(Room room, User meetingCreator, List<User> participants, GregorianCalendar dateStart,GregorianCalendar dateEnd, String meetingSubject)
    {
        this.room = room;
        this.meetingCreator = meetingCreator;
        this.participants = participants;
        this.dateStart=dateStart;
        this.dateEnd=dateEnd;
        this.meetingSubject=meetingSubject;
    }
    public Meeting()
    {
        participants = new ArrayList<>();
    }

    public Room getRoom() {
        return room;
    }

    public void setDateEnd(GregorianCalendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDateStart(GregorianCalendar dateStart) {
        this.dateStart = dateStart;
    }

    public GregorianCalendar getDateEnd() {
        return dateEnd;
    }

    public GregorianCalendar getDateStart() {
        return dateStart;
    }

    public String getMeetingInfo()
    {
        String roomName = room.getName();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String dateTime = dateFormat.format(dateStart.getTime());
        String newDateTime = dateTime.replace(':','h');
        return meetingSubject + " - "+newDateTime+" - "+ roomName;

    }
    public String getParticipantsEmail()
    {
        String result="";
        if(participants!=null && participants.size()!=0)
        {
            for(User user:participants)
            {
                result+= user.getEmail()+", ";
            }
        }

        return result;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setStartDate(GregorianCalendar date) {
        this.dateStart = date;
    }
    public void addParticipant(User participant)
    {
        participants.add(participant);
    }
    public void removeParticipant(User participant)
    {
        participants.remove(participant);
    }

    public void setMeetingCreator(User meetingCreator) {
        this.meetingCreator = meetingCreator;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
