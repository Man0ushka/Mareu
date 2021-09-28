package com.example.mareu.controller;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mareu.model.Room;

import java.util.ArrayList;
import java.util.List;

public class SpinnerController {
    public static void setRoomListSpinner(List<Room> roomList, Spinner spinner, Activity activity)
    {
        List<String> roomNames = new ArrayList<>();
        for(Room room:roomList)
        {
            roomNames.add(room.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, roomNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }
}
