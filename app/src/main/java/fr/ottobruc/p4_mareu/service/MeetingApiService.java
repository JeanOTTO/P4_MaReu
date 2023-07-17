package fr.ottobruc.p4_mareu.service;

import java.util.List;

import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;

public interface MeetingApiService {

    List<Meeting> getMeetings();
    List<Room> getRooms();
    List<User> getUsers();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);
}
