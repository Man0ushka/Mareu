package com.example.mareu.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class Meeting {
    Room room;
    User meetingCreator;
    List<User> participants;
    GregorianCalendar dateStart;
    GregorianCalendar dateEnd;
    String meetingSubject;

    public Meeting(Room room, User meetingCreator, List<User> participants, GregorianCalendar dateStart, GregorianCalendar dateEnd, String meetingSubject) {
        this.room = room;
        this.meetingCreator = meetingCreator;
        this.participants = participants;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.meetingSubject = meetingSubject;
    }

    public Meeting() {
        participants = new ArrayList<>();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public GregorianCalendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(GregorianCalendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public GregorianCalendar getDateStart() {
        return dateStart;
    }


    public String getMeetingInfo() {
        String roomName = room.getName();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.FRANCE);
        String dateTime = dateFormat.format(dateStart.getTime());
        String newDateTime = dateTime.replace(':', 'h');
        return meetingSubject + " - " + newDateTime + " - " + roomName;
    }

    public String getParticipantsEmail() {
        StringBuilder result = new StringBuilder();
        if (participants != null && participants.size() != 0) {
            for (User user : participants) {
                result.append(user.getEmail()).append(", ");
            }
        }
        return result.toString();
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setStartDate(GregorianCalendar date) {
        this.dateStart = date;
    }

    public void addParticipant(User participant) {
        participants.add(participant);
    }

    public void removeParticipant(User participant) {
        participants.remove(participant);
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }
}
