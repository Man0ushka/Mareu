package com.example.mareu;

import com.example.mareu.DI.ServiceDI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.User;
import com.example.mareu.service.meetings.MeetingsApiService;
import com.example.mareu.service.meetings.MeetingsGenerator;
import com.example.mareu.service.rooms.RoomsGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingsApiService service;

    @Before
    public void setUp()
    {
        service = (MeetingsApiService) ServiceDI.getNewInstanceMeetingsApiService();
    }
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = MeetingsGenerator.MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }
    @Test
    public void filterResetShouldDisplayOriginalMeetings()
    {
        List<Meeting> meetings = service.getMeetings();
        String filterType = "";
        String filterText = "";
        service.filterMeetings(filterText,filterType);
        List<Meeting> actualMeetings = service.getMeetingsDisplayed();
        assertThat(actualMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(meetings.toArray()));
    }
    @Test
    public void filterRoomShouldOnlyDisplaySelectedRoom()
    {
        List<Meeting> meetings = service.getMeetings();
        String filterType = "room";
        String filterText = "Room 1";
        List<Meeting> expectedMeetings = new ArrayList<>(Arrays.asList(meetings.get(0)));
        service.filterMeetings(filterText,filterType);
        List<Meeting> actualMeetings = service.getMeetingsDisplayed();
        assertThat(actualMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }
    @Test
    public void filterDateShouldOnlyDisplaySelectedDate()
    {
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.set(2021,11,12);
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.set(2021,11,12);
        Meeting newMeeting = new Meeting(RoomsGenerator.ROOMS.get(0),
                new User("Manu","manu.james@gmail.fr"),
                Arrays.asList(new User("Manu","manu.James@gmail.fr")),
                startDate,
                endDate,
                "My meeting");
        service.createMeeting(newMeeting);
        String filterType = "date";
        String filterText = "12/12/21";
        List<Meeting> expectedMeetings = new ArrayList<>(Arrays.asList(newMeeting));
        service.filterMeetings(filterText,filterType);
        List<Meeting> actualMeetings = service.getMeetingsDisplayed();
        assertThat(actualMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }
}
