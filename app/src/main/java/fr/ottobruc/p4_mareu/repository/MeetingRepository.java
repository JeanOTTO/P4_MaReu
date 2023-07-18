package fr.ottobruc.p4_mareu.repository;

import static fr.ottobruc.p4_mareu.utils.DateTimeUtil.isSameDate;

import java.util.ArrayList;
import java.util.Date;
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

    public List<Meeting> getFilteredMeetingsByDate(Date selectedDate) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        for (Meeting meeting : apiService.getMeetings()) {
            if (isSameDate(meeting.getDate(), selectedDate)) {
                filteredMeetings.add(meeting);
            }
        }
        return filteredMeetings;
    }

    public List<Meeting> getFilteredMeetingsByRoom(Room room) {
        List<Meeting> filteredMeetings = new ArrayList<>();
        for (Meeting meeting : apiService.getMeetings()) {
            if (meeting.getLocation().getName().equals(room.getName())) {
                filteredMeetings.add(meeting);
            }
        }
        return filteredMeetings;
    }

}
