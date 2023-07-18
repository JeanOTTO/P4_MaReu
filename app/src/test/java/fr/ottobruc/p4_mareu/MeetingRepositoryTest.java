package fr.ottobruc.p4_mareu;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.ottobruc.p4_mareu.di.DI;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;
import fr.ottobruc.p4_mareu.repository.MeetingRepository;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingRepositoryTest {

    private MeetingRepository repository;

    @Before
    public void setup() {
        repository = new MeetingRepository(DI.getNewInstanceApiService());
    }

    @Test
    public void addMeeting_shouldAddMeetingToList() {
        // Arrange
        Meeting meeting = createMeeting();
        int size = repository.getMeetings().size();

        // Act
        repository.addMeeting(meeting);

        // Assert
        assertEquals(size+1, repository.getMeetings().size());
        assertEquals(meeting, repository.getMeetings().get(size));
    }

    @Test
    public void removeMeeting_shouldRemoveMeetingFromList() {
        // Arrange
        Meeting meeting1 = createMeeting();
        Meeting meeting2 = createMeeting();
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);
        int size = repository.getMeetings().size();

        // Act
        repository.deleteMeeting(meeting1);

        // Assert
        assertEquals(size-1, repository.getMeetings().size());
        assertEquals(meeting2, repository.getMeetings().get(size-2));
    }

    @Test
    public void getFilteredMeetingsByDate_shouldReturnFilteredMeetings() {
        // Arrange
        Meeting meeting1 = createMeeting();
        Meeting meeting2 = createMeeting();
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 5, 1); // June 1, 2023
        Date selectedDate = calendar.getTime();

        // Act
        List<Meeting> filteredMeetings = repository.getFilteredMeetingsByDate(selectedDate);

        // Assert
        assertEquals(2, filteredMeetings.size());
    }

    @Test
    public void getFilteredMeetingsByRoom_shouldReturnFilteredMeetings() {
        // Arrange
        Meeting meeting1 = createMeeting();
        Meeting meeting2 = createMeeting();
        repository.addMeeting(meeting1);
        repository.addMeeting(meeting2);
        Room selectedRoom = new Room("Room A");

        // Act
        List<Meeting> filteredMeetings = repository.getFilteredMeetingsByRoom(selectedRoom);

        // Assert
        assertEquals(2, filteredMeetings.size());
    }

    private Meeting createMeeting() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 5, 1, 10, 0); // June 1, 2023, 10:00 AM
        Date startTime = calendar.getTime();
        calendar.set(2023, 5, 1, 11, 0); // June 1, 2023, 11:00 AM
        Date endTime = calendar.getTime();
        calendar.set(2023, 5, 1); // June 1, 2023
        Date date = calendar.getTime();
        Room room = new Room("Room A");
        List<User> participants = new ArrayList<>();
        participants.add(new User("john@example.com"));
        participants.add(new User("emma@example.com"));
        return new Meeting(1, startTime, endTime, date, room, "Meeting Subject", participants);
    }
}
