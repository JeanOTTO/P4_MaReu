package fr.ottobruc.p4_mareu.controler;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.notNullValue;
import static fr.ottobruc.p4_mareu.controler.utils.RecyclerViewItemCountAssertion.withItemCount;
import static fr.ottobruc.p4_mareu.service.DummyMeetingGenerator.DUMMY_MEETINGS;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import fr.ottobruc.p4_mareu.R;
import fr.ottobruc.p4_mareu.controler.utils.DeleteViewAction;
import fr.ottobruc.p4_mareu.di.DI;
import fr.ottobruc.p4_mareu.repository.MeetingRepository;
import fr.ottobruc.p4_mareu.service.DummyMeetingGenerator;

import androidx.test.filters.LargeTest;
import java.util.Calendar;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListMeetingActivityTest {

    @Rule
    public ActivityScenarioRule<ListMeetingActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(ListMeetingActivity.class);

    @Before
    public void setUp() {
        DI.getNewInstanceApiService();
        assertThat(mActivityScenarioRule, notNullValue());
    }

    @Test
    public void meetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.meetingRecyclerView))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetingList_shouldRemoveMeeting() {
        onView(withId(R.id.meetingRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView((withId(R.id.meetingRecyclerView)))
                .check(withItemCount(DUMMY_MEETINGS.size() - 1));
    }

    @Test
    public void meetingList_shouldOpenAddMeetingActivity() {
        onView(withId(R.id.addMeetingButton)).perform(click());
        onView(withId(R.id.addMeetingScrollView)).check(matches(isDisplayed()));
    }

    @Test
    public void meetingList_shouldFilterMeetingByDate() {
        onView(withId(R.id.filter)).perform(click());
        onView(withText("Filtrer par date")).perform(click());

        // Sélectionne la date du 28/07/2023 (1 réunion à cette date) dans le DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.DAY_OF_MONTH, 28);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));

        onView(withText("OK")).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(1));
    }

    @Test
    public void meetingList_shouldFilterMeetingByRoom() {
        MeetingRepository repository = DI.getMeetingRepository();
        int expectedMeeting =
                repository.getFilteredMeetingsByRoom(repository.getRooms().get(0)).size()
                        +repository.getFilteredMeetingsByRoom(repository.getRooms().get(1)).size()
                        +repository.getFilteredMeetingsByRoom(repository.getRooms().get(2)).size();

        onView(withId(R.id.filter)).perform(click());
        onView(withText("Filtrer par salle")).perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withId(androidx.appcompat.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.appcompat.R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatCheckedTextView.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(allOf(withId(androidx.appcompat.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.appcompat.R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(allOf(withId(androidx.appcompat.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.appcompat.R.id.contentPanel),
                                0)))
                .atPosition(2);
        appCompatCheckedTextView3.perform(click());

        onView(withText("Filtrer")).perform(click());
        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(expectedMeeting));
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
