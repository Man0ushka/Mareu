package com.example.mareu.view.list_meetings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.DI.ServiceDI;
import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.meetings.IMeetingsApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MeetingFragment extends Fragment {
    private List<Meeting> mMeetingsListFiltered;
    private IMeetingsApiService mMeetingsApiService;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public MyMeetingRecyclerViewAdapter getMyMeetingRecyclerViewAdapter() {
        return myMeetingRecyclerViewAdapter;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MeetingFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MeetingFragment newInstance(int columnCount) {
        MeetingFragment fragment = new MeetingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mMeetingsApiService = ServiceDI.getMeetingsApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        }
        return view;
    }
    public void initList()
    {

        mMeetingsListFiltered = mMeetingsApiService.getMeetingsDisplayed();

        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(mMeetingsListFiltered);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initList();

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void OnDeleteMeeting(DeleteMeetingEvent event)
    {
            mMeetingsApiService.deleteMeeting(event.getMeeting());
            initList();
    }

}