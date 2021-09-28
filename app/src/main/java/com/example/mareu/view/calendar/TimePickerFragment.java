package com.example.mareu.view.calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.events.SetMeetingTimeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    String tag;
    public TimePickerFragment(String tag){
        this.tag=tag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24Hour = DateFormat.is24HourFormat(getActivity());
        is24Hour=true;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                is24Hour);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EventBus.getDefault().post(new SetMeetingTimeEvent(tag, hourOfDay,minute));
    }
}
