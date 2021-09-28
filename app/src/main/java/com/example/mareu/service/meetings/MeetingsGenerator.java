package com.example.mareu.service.meetings;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.User;
import com.example.mareu.service.rooms.RoomsGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class MeetingsGenerator {

    public static List<Meeting> MEETINGS = Arrays.asList(
            new Meeting(RoomsGenerator.ROOMS.get(0),
                    new User("Manu","manu.james@gmail.fr"),
                    Arrays.asList(new User("Manu","manu.James@gmail.fr")),
                    new GregorianCalendar(),
                    new GregorianCalendar(),
                    "My meeting"),
            new Meeting(RoomsGenerator.ROOMS.get(1),
                    new User("Antoine","antoine.gerome@gmail.fr"),
                    Arrays.asList(new User("Antoine","antoine.gerome@gmail.fr")),
                    new GregorianCalendar(),
                    new GregorianCalendar(),
                    "My meeting 2"),
            new Meeting(RoomsGenerator.ROOMS.get(2),
                    new User("Etienne","etienne.roger@gmail.fr"),
                    Arrays.asList(new User("Etienne","etienne.roger@gmail.fr")),
                    new GregorianCalendar(),
                    new GregorianCalendar(),
                    "My meeting 3"));

    public static List<Meeting> generateMeetingsList()
    {
        return new ArrayList<>(MEETINGS);
    }

    public static List<Meeting> MEETINGS_DISPLAYED = Collections.emptyList();

    public static List<Meeting> generateDisplayedMeetingsList(){return new ArrayList<>(MEETINGS_DISPLAYED);}

}
