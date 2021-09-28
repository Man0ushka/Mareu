package com.example.mareu;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.mareu.DI.ServiceDI;
import com.example.mareu.service.meetings.MeetingsApiService;
import com.example.mareu.view.add_meeting.AddMeeting;
import com.example.mareu.view.list_meetings.ListMeetings;
import com.example.mareu.utils.DeleteViewAction;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.adevinta.android.barista.interaction.BaristaMenuClickInteractions.clickMenu;
import static com.adevinta.android.barista.interaction.BaristaMenuClickInteractions.openMenu;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MeetingListInstrumentedTest {
    private static int ITEMS_COUNT = 3;

    @Rule
    public final IntentsTestRule<ListMeetings> mActivityRule =
            new IntentsTestRule<>(ListMeetings.class);

    private ListMeetings mActivity;
    private MeetingsApiService meetingsApiService;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        meetingsApiService = (MeetingsApiService) ServiceDI.getMeetingsApiService();
        assertThat(mActivity, notNullValue());
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mareu", appContext.getPackageName());
    }


    @Test
    public void listMeetings_addButton_shouldOpenAddMeetingActivity()
    {
        // click on add button
        onView(ViewMatchers.withId(R.id.add_meeting_button)).perform(click());
        // Check if viewing detail activity is launched
        intended(hasComponent(AddMeeting.class.getName()));
    }
    @Test
    public void addMeeting_confirmation_shouldAddMeetingToMeetingsList() {
        int itemCount = meetingsApiService.getMeetings().size();
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(itemCount));
        // click on add button
        onView(ViewMatchers.withId(R.id.add_meeting_button)).perform(click());
        // type subject of meeting
        onView(ViewMatchers.withId(R.id.meeting_subject_text)).perform(replaceText("My meeting"));
        // add participant
        onView(ViewMatchers.withId(R.id.add_participants_text_input)).perform(replaceText("test@live.fr"));
        onView(ViewMatchers.withId(R.id.add_participants_input_layout)).perform(clickIcon(true));
        // set start date and time
        setDate(R.id.date_btn_start,2021,9,26);
        setTime(R.id.time_btn_start,8,10);
        // set end date and time
        setDate(R.id.date_btn_end,2021,9,26);
        setTime(R.id.time_btn_end,9,10);
        // set room 4
        onView(ViewMatchers.withId(R.id.room_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        // click on confirmation
        onView(ViewMatchers.withId(R.id.add_meeting_confirmation_btn)).perform(click());

        // check if meeting is added and in the list
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(itemCount+1));
        onView(withText("My meeting - 08h10 - Room 4")).check(matches(isDisplayed()));
        onView(withText("test@live.fr, ")).check(matches(isDisplayed()));

    }
    @Test
    public void filterRoom_shouldOnlyDisplaySelectedRoom()
    {

        // Add meeting in room 5
        onView(ViewMatchers.withId(R.id.add_meeting_button)).perform(click());
        onView(ViewMatchers.withId(R.id.meeting_subject_text)).perform(replaceText("Room check"));
        setDate(R.id.date_btn_start,2021,9,26);
        setTime(R.id.time_btn_start,10,10);
        setDate(R.id.date_btn_end,2021,9,26);
        setTime(R.id.time_btn_end,11,10);
        onView(ViewMatchers.withId(R.id.room_spinner)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(ViewMatchers.withId(R.id.add_meeting_confirmation_btn)).perform(click());
        // Filter room 5
        // Open menu
        //openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        //openContextualActionModeOverflowMenu();
        openMenu();
        // Click on filter room
        clickMenu(R.id.filter_room);
        // Click on room 5
        onData(anything()).atPosition(5).perform(click());
        // Check only one room is displayed
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(1));
        onView(withText("Room check - 10h10 - Room 5")).check(matches(isDisplayed()));
        openMenu();
        onData(anything()).atPosition(0).perform(click());
    }
    @Test
    public void filterDate_shouldOnlyDisplaySelectedDate()
    {

        // Add meeting in room 5
        onView(ViewMatchers.withId(R.id.add_meeting_button)).perform(click());
        onView(ViewMatchers.withId(R.id.meeting_subject_text)).perform(replaceText("Date check"));
        setDate(R.id.date_btn_start,2021,7,26);
        setTime(R.id.time_btn_start,10,10);
        setDate(R.id.date_btn_end,2021,7,26);
        setTime(R.id.time_btn_end,11,10);
        onView(ViewMatchers.withId(R.id.room_spinner)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(ViewMatchers.withId(R.id.add_meeting_confirmation_btn)).perform(click());
        // Filter date
        // Open menu
        openMenu();
        // Click on filter date
        clickMenu(R.id.filter_date);
        // Set date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021,7,26));
        onView(withId(android.R.id.button1)).perform(click());
        // Check only one room is displayed
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(1));
        onView(withText("Date check - 10h10 - Room 6")).check(matches(isDisplayed()));
        openMenu();
        onData(anything()).atPosition(0).perform(click());
    }
    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        int itemCount = meetingsApiService.getMeetings().size();
        // Given : We remove the element at position 1
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(itemCount));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(itemCount-1));

    }
    public static void setDate(int buttonId, int year, int month, int day)
    {
        onView(ViewMatchers.withId(buttonId)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(ViewMatchers.withId(android.R.id.button1)).perform(click());
    }
    public static void setTime(int buttonId, int hours, int minutes)
    {
        onView(ViewMatchers.withId(buttonId)).perform(click());
        onView(ViewMatchers.withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hours, minutes));
        onView(ViewMatchers.withId(android.R.id.button1)).perform(click());
    }

    public static ViewAction clickIcon(final boolean isEndIcon) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TextInputLayout.class);
            }

            @Override
            public String getDescription() {
                return "Clicks the end or start icon";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextInputLayout item = (TextInputLayout) view;
                // Reach in and find the icon view since we don't have a public API to get a reference to it
                CheckableImageButton iconView =
                        item.findViewById(isEndIcon ? R.id.text_input_end_icon : R.id.text_input_start_icon);
                iconView.performClick();
            }
        };
    }
}
