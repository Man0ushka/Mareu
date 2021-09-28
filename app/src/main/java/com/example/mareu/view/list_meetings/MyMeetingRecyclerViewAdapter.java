package com.example.mareu.view.list_meetings;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;

import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Meeting}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;

    public MyMeetingRecyclerViewAdapter(List<Meeting> itemsFiltered) {
        mMeetings = itemsFiltered;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);

      //  return new ViewHolder(FragmentMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mMeetingText.setText(meeting.getMeetingInfo());
        holder.mParticipants.setText(meeting.getParticipantsEmail());
//        float textSize = holder.mMeetingText.getPaint().getTextSize();
//        Log.d("top","top size: "+textSize);
//        holder.mParticipants.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize/2);
//        Log.d("bottom", "bottom size: "+holder.mParticipants.getTextSize());

        holder.mNeighbourAvatar.setColorFilter(ContextCompat.getColor(holder.mNeighbourAvatar.getContext(), meeting.getRoom().getColorId()));

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting,v));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mMeetingText;
        @BindView(R.id.item_list_participants)
        public TextView mParticipants;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
        public TextView getmMeetingText()
        {
            return mMeetingText;
        }

    }
}