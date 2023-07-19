package fr.ottobruc.p4_mareu.controler;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static fr.ottobruc.p4_mareu.controler.utils.RecyclerViewItemCountAssertion.withItemCount;
import static fr.ottobruc.p4_mareu.service.DummyMeetingGenerator.DUMMY_MEETINGS;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.text.ParseException;

import fr.ottobruc.p4_mareu.R;
import fr.ottobruc.p4_mareu.di.DI;

@RunWith(AndroidJUnit4.class)
public class AddMeetingActivityTest {

    @Rule
    public ActivityScenarioRule<ListMeetingActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(ListMeetingActivity.class);

    @Before
    public void setUp() {
        DI.getNewInstanceApiService();
        assertThat(mActivityScenarioRule, notNullValue());
    }

    @Test
    public void createMeeting_shouldCreate() throws ParseException {
onView(withId(R.id.addMeetingButton)).perform(click());


        onView(withId(R.id.subjectEditText)).perform(replaceText("TEST"));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.dateEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dateTextInputLayout),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.startTimeEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.startTimeTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        onView(withId(R.id.roomAutoCompleteTextview)).perform(click());
        onView(withText("Mario"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());


        onView(withId(R.id.participantAutoCompleteTextview)).perform(replaceText("test@test.com"));

        onView(withId(R.id.addParticipantButton)).perform(click());

        onView(withId(R.id.createMeetingButton)).perform(click());

        onView(withId(R.id.meetingRecyclerView)).check(withItemCount(DUMMY_MEETINGS.size() +1) );
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