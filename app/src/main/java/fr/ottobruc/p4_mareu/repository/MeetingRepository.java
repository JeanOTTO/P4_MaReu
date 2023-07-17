package fr.ottobruc.p4_mareu.repository;

import java.util.List;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;
import fr.ottobruc.p4_mareu.service.MeetingApiService;

public class MeetingRepository {
    private final MeetingApiService apiService;

    public MeetingRepository(MeetingApiService apiService) {
        this.apiService = apiService;
    }

    public List<Meeting> getMeetings() {
        return apiService.getMeetings();
    }

    public void addMeeting(Meeting meeting) {
        apiService.addMeeting(meeting);
    }

    public void deleteMeeting(Meeting meeting) {
        apiService.deleteMeeting(meeting);
    }

    public List<Room> getRooms() {
        return apiService.getRooms();
    }

    public List<User> getUsers() {
        return apiService.getUsers();
    }

}
