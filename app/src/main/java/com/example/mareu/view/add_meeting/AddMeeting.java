package com.example.mareu.view.add_meeting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.example.mareu.DI.ServiceDI;
import com.example.mareu.R;
import com.example.mareu.controller.AddMeetingController;
import com.example.mareu.controller.SpinnerController;
import com.example.mareu.events.SetMeetingDateEvent;
import com.example.mareu.events.SetMeetingTimeEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.model.User;
import com.example.mareu.service.meetings.IMeetingsApiService;
import com.example.mareu.service.rooms.RoomsGenerator;
import com.example.mareu.view.calendar.DatePickerFragment;
import com.example.mareu.view.calendar.TimePickerFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddMeeting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button dateStartButton;
    Button timeStartButton;
    Button dateEndButton;
    Button timeEndButton;
    Meeting meeting;
    Spinner roomSpinner;
    TextView subjectMeetingText;
    TextInputLayout addParticipantLayout;
    TextView addParticipantText;
    Button creationMeetingBtn;
    GregorianCalendar dateStart;
    GregorianCalendar dateEnd;
    ChipGroup chipGroup;
    IMeetingsApiService meetingsService;
    List<Meeting> meetingList;
    List<Room> roomList;
    Room room;
    String roomName;
    AddMeetingController addMeetingController;

    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeeting.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public static String formatString(int input) {
        if (input >= 0 && input <= 9) {
            return "0" + input;
        } else return "" + input;
    }

    public static void updateDatePickerText(Button button, int year, int month, int dayOfMonth) {
        button.setText(String.format("%s/%s/%d", formatString(dayOfMonth), formatString(month), year));
    }

    public static void updateTimePickerText(Button button, int hour, int minute) {
        button.setText(String.format("%sh%s", formatString(hour), formatString(minute)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        setTitle(getString(R.string.add_meeting));
        init();
        clickListenersInit();
    }
    public void init()
    {
        dateStartButton = findViewById(R.id.date_btn_start);
        timeStartButton = findViewById(R.id.time_btn_start);
        dateEndButton = findViewById(R.id.date_btn_end);
        timeEndButton = findViewById(R.id.time_btn_end);
        roomSpinner = findViewById(R.id.room_spinner);
        subjectMeetingText = findViewById(R.id.meeting_subject_text);
        creationMeetingBtn = findViewById(R.id.add_meeting_confirmation_btn);
        addParticipantLayout = findViewById(R.id.add_participants_input_layout);
        addParticipantText = findViewById(R.id.add_participants_text_input);
        chipGroup = findViewById(R.id.chipGroup);
        meetingsService = ServiceDI.getMeetingsApiService();
        meetingList = meetingsService.getMeetings();
        addMeetingController = new AddMeetingController(meetingList);
        meeting = new Meeting();
        dateStart = new GregorianCalendar();
        dateEnd = new GregorianCalendar();
        roomList = RoomsGenerator.generateRoomList();
        room = new Room();
        SpinnerController.setRoomListSpinner(roomList, roomSpinner, this);
        roomSpinner.setOnItemSelectedListener(this);
    }
    public void clickListenersInit()
    {
        creationMeetingBtn.setOnClickListener(v -> addMeeting());
        addParticipantLayout.setEndIconOnClickListener(v -> {
            if (addParticipantText.getText().length() > 0) {
                meeting.addParticipant(new User(addParticipantText.getText().toString()));
                Chip chip = new Chip(AddMeeting.this);
                chip.setText(addParticipantText.getText().toString());
                chip.setChipBackgroundColorResource(R.color.white);
                chip.setCloseIconVisible(true);
                chip.setTextColor(getResources().getColor(R.color.black));
                chipGroup.addView(chip);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(View v) {
                        meeting.removeParticipant(meeting.getParticipants().get(chipGroup.getRowIndex(chip)));
                        chipGroup.removeView(chip);
                    }
                });
                addParticipantText.setText("");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roomName = (String) parent.getItemAtPosition(position);
        room = roomList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment((String) v.getTag());
        newFragment.show(getSupportFragmentManager(), (String) v.getTag());
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment((String) v.getTag());
        newFragment.show(getSupportFragmentManager(), (String) v.getTag());
    }

    @Subscribe
    public void onDateSetMeetingEvent(SetMeetingDateEvent event) {
        int year = event.getYear();
        int month = event.getMonth();
        int day = event.getDayOfMonth();
        String tag = event.getTag();
        if (tag == dateStartButton.getTag()) {
            updateDatePickerText(dateStartButton, year, month, day);
            dateStart.clear();
            dateStart.set(year, month, day);
        } else {
            updateDatePickerText(dateEndButton, year, month, day);
            dateEnd.clear();
            dateEnd.set(year, month, day);
        }
    }

    @Subscribe
    public void onTimeSetMeetingEvent(SetMeetingTimeEvent event) {
        int hour = event.getHour();
        int minute = event.getMinute();
        String tag = event.getTag();
        if (tag == timeStartButton.getTag()) {
            updateTimePickerText(timeStartButton, hour, minute);
            dateStart.set(Calendar.HOUR_OF_DAY, hour);
            dateStart.set(Calendar.MINUTE, minute);
            Date expectedDate = dateStart.getTime();
            System.out.println("Start date: " + expectedDate);
            meeting.setStartDate(dateStart);
        } else {
            updateTimePickerText(timeEndButton, hour, minute);
            dateEnd.set(Calendar.HOUR_OF_DAY, hour);
            dateEnd.set(Calendar.MINUTE, minute);
            Date expectedDate = dateEnd.getTime();
            System.out.println("End date: " + expectedDate);
            meeting.setDateEnd(dateEnd);
            // Actualiser liste rooms
            addMeetingController.checkForAvailableRooms(meeting, roomList);
            SpinnerController.setRoomListSpinner(roomList, roomSpinner, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    void addMeeting() {
        if (room != null)
            meeting.setRoom(room);
        else {
            Toast.makeText(AddMeeting.this, "Please choose a room", Toast.LENGTH_SHORT).show();
            return;
        }
        if (subjectMeetingText.getText() != null)
            meeting.setMeetingSubject(subjectMeetingText.getText().toString());
        if (meeting.getDateStart() == null) {
            Toast.makeText(AddMeeting.this, "Please choose a start date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (meeting.getDateEnd() == null) {
            Toast.makeText(AddMeeting.this, "Please choose an end date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (roomList.size() == 0) {
            Toast.makeText(AddMeeting.this, "No room available, please choose a different time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (meeting.getDateStart().after(meeting.getDateEnd())) {
            Toast.makeText(AddMeeting.this, "Be careful, the beginning of the meeting is set after the end!", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((meeting.getDateEnd().getTimeInMillis() - meeting.getDateStart().getTimeInMillis()) / (1000 * 60) < 15) {
            Toast.makeText(AddMeeting.this, "Please book the room for more than 15mn, thank you!", Toast.LENGTH_SHORT).show();
            return;
        }
        meetingsService.createMeeting(meeting);
        finish();
    }
}