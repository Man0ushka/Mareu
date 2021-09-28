package com.example.mareu.model;

import android.graphics.Color;

import com.example.mareu.R;

public class Room {
    String name;
    int number;
    int colorId;

    public Room(String name, int number)
    {
        this.name=name;
        this.number=number;
        setColorId();

    }
    public Room(String name)
    {
        this.name=name;
    }

    public Room() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId()
    {
        switch(number)
        {
            case 1:
            {
                colorId=R.color.Room1;
                break;
            }
            case 2:
            {
                colorId=R.color.Room2;
                break;
            }
            case 3:
            {
                colorId=R.color.Room3;
                break;
            }
            case 4:
            {
                colorId=R.color.Room4;
                break;
            }
            case 5:
            {
                colorId=R.color.Room5;
                break;
            }
            case 6:
            {
                colorId=R.color.Room6;
                break;
            }
            case 7:
            {
                colorId=R.color.Room7;
                break;
            }
            case 8:
            {
                colorId=R.color.Room8;
                break;
            }
            case 9:
            {
                colorId=R.color.Room9;
                break;
            }
            case 10:
            {
                colorId=R.color.Room10;
                break;
            }
            default:
            {
                break;
            }
        }
    }
}
