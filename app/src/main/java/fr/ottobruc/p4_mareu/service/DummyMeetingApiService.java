package fr.ottobruc.p4_mareu.service;

import java.util.List;

import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;

public class DummyMeetingApiService implements MeetingApiService{

/**
 * Dummy mock for the Api
 */

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<Room> rooms = DummyMeetingGenerator.generateRooms();
    private List<User> users = DummyMeetingGenerator.generateUsers();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }
    @Override
    public List<Room> getRooms() {
        return rooms;
    }
    @Override
    public List<User> getUsers() {
        return users;
    }
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}
