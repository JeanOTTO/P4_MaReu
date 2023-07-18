package fr.ottobruc.p4_mareu.service;

import java.util.List;

import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;

/**
 * DummyMeetingApiService is a mock class implementing the MeetingApiService interface.
 * It generates a dummy data set for testing purposes.
 */
public class DummyMeetingApiService implements MeetingApiService{

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<Room> rooms = DummyMeetingGenerator.generateRooms();
    private List<User> users = DummyMeetingGenerator.generateUsers();

    /**
     * @return a list of all dummy meetings.
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * @return a list of all dummy rooms.
     */
    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @return a list of all dummy users.
     */
    @Override
    public List<User> getUsers() {
        return users;
    }

    /**
     * Deletes a specific dummy meeting.
     *
     * @param meeting Meeting to be deleted.
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * Adds a new dummy meeting.
     *
     * @param meeting Meeting to be added.
     */
    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}
