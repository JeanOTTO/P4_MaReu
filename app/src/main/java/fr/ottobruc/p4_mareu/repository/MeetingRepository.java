package fr.ottobruc.p4_mareu.repository;

import static fr.ottobruc.p4_mareu.utils.DateTimeUtil.isSameDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;
import fr.ottobruc.p4_mareu.service.MeetingApiService;

/**
 * A class that serves as a repository for managing Meeting, Room and User data.
 */
public class MeetingRepository {
    private final MeetingApiService apiService;

    /**
     * Construct a new MeetingRepository.
     *
     * @param apiService The service to manage Meetings, Rooms, and Users.
     */
    public MeetingRepository(MeetingApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Get all meetings.
     *
     * @return a list of all meetings.
     */
    public List<Meeting> getMeetings() {
        return apiService.getMeetings();
    }

    /**
     * Add a meeting.
     *
     * @param meeting The meeting to be added.
     */
    public void addMeeting(Meeting meeting) {
        apiService.addMeeting(meeting);
    }

    /**
     * Delete a meeting.
     *
     * @param meeting The meeting to be deleted.
     */
    public void deleteMeeting(Meeting meeting) {
        apiService.deleteMeeting(meeting);
    }

    /**
     * Get all rooms.
     *
     * @return a list of all rooms.
     */
    public List<Room> getRooms() {
        return apiService.getRooms();
    }

    /**
     * Get all users.
     *
     * @return a list of all users.
     */
    public List<User> getUsers() {
        return apiService.getUsers();
    }

    /**
     * Filter meetings by a selected date.
     *
     * @param selectedDate The date to filter meetings by.
     * @return a list of meetings on the selected date.
     */
    public List<Meeting> getFilteredMeetingsByDate(Date selectedDate) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        for (Meeting meeting : apiService.getMeetings()) {
            if (isSameDate(meeting.getDate(), selectedDate)) {
                filteredMeetings.add(meeting);
            }
        }
        return filteredMeetings;
    }

    /**
     * Filter meetings by room.
     *
     * @param room The room to filter meetings by.
     * @return a list of meetings in the selected room.
     */
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
