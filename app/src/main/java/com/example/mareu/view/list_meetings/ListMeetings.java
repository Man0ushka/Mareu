package com.example.mareu.view.list_meetings;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mareu.DI.ServiceDI;
import com.example.mareu.R;
import com.example.mareu.events.SetMeetingDateEvent;
import com.example.mareu.service.meetings.IMeetingsApiService;
import com.example.mareu.view.add_meeting.AddMeeting;
import com.example.mareu.view.calendar.DatePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ListMeetings extends AppCompatActivity {

    FloatingActionButton mAddMeetingBtn;
    MyMeetingRecyclerViewAdapter adapter;
    MeetingFragment fragment;
    IMeetingsApiService mMeetingsApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        setTitle("Ma réu");
        mAddMeetingBtn = findViewById(R.id.add_meeting_button);

        mMeetingsApiService = ServiceDI.getMeetingsApiService();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = MeetingFragment.newInstance(1);
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
        mAddMeetingBtn.setOnClickListener(v -> AddMeeting.navigate(ListMeetings.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
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

    @Subscribe
    public void onDateSetMeetingEvent(SetMeetingDateEvent event) {
        int year = event.getYear();
        int month = event.getMonth();
        int day = event.getDayOfMonth();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(year, month, day);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
        mMeetingsApiService.filterMeetings(dateFormat.format(gregorianCalendar.getTime()), "date");
        adapter = fragment.getMyMeetingRecyclerViewAdapter();
        adapter.notifyDataSetChanged();
        setTitle("Ma réu - " + dateFormat.format(gregorianCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        adapter = fragment.getMyMeetingRecyclerViewAdapter();
        if (item.getItemId() != R.id.filter_date && item.getItemId() != R.id.filter_reset && item.getItemId() != R.id.filter_room) {
            mMeetingsApiService.filterMeetings((String) item.getTitle(), "room");
            adapter.notifyDataSetChanged();
            setTitle("Ma réu - " + item.getTitle());
        }
        switch (item.getItemId()) {
            case R.id.filter_date: {
                DialogFragment newFragment = new DatePickerFragment("filterDate");
                newFragment.show(getSupportFragmentManager(), "filterDate");
                break;
            }


            case R.id.filter_reset: {
                mMeetingsApiService.filterMeetings("", "");
                adapter.notifyDataSetChanged();
                setTitle("Ma réu");
                break;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

}