package fr.ottobruc.p4_mareu.service;

import java.util.List;

import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;

/**
 * MeetingApiService interface defines the operations that can be performed on meetings.
 */
public interface MeetingApiService {

    /**
     * @return a list of all meetings.
     */
    List<Meeting> getMeetings();

    /**
     * @return a list of all rooms.
     */
    List<Room> getRooms();

    /**
     * @return a list of all users.
     */
    List<User> getUsers();

    /**
     * Delete a specific meeting.
     *
     * @param meeting Meeting to be deleted.
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Add a new meeting.
     *
     * @param meeting Meeting to be added.
     */
    void addMeeting(Meeting meeting);
}
