package com.example.mareu.service.rooms;

import com.example.mareu.model.Room;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomsGenerator {
    public static List<Room> ROOMS = Arrays.asList(new Room("Room 1", 1)
            , new Room("Room 2", 2)
            , new Room("Room 3", 3)
            , new Room("Room 4", 4)
            , new Room("Room 5", 5)
            , new Room("Room 6", 6)
            , new Room("Room 7", 7)
            , new Room("Room 8", 8)
            , new Room("Room 9", 9)
            , new Room("Room 10", 10));

    public static List<Room> generateRoomList() {
        return new ArrayList<>(ROOMS);
    }

}
