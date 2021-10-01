package com.example.mareu.DI;

import com.example.mareu.service.meetings.IMeetingsApiService;
import com.example.mareu.service.meetings.MeetingsApiService;

public class ServiceDI {
    public static IMeetingsApiService meetingsApiService = new MeetingsApiService();

    public static IMeetingsApiService getMeetingsApiService() {
        return meetingsApiService;
    }

    public static IMeetingsApiService getNewInstanceMeetingsApiService() {
        meetingsApiService = new MeetingsApiService();
        return meetingsApiService;
    }
}
