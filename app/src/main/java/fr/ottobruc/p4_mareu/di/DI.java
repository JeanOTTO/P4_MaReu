package fr.ottobruc.p4_mareu.di;

import fr.ottobruc.p4_mareu.repository.MeetingRepository;
import fr.ottobruc.p4_mareu.service.DummyMeetingApiService;
import fr.ottobruc.p4_mareu.service.MeetingApiService;

public class DI {
    private static MeetingRepository meetingRepository;
    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new DummyMeetingApiService());
    }

    public static MeetingRepository getMeetingRepository() {
        if (meetingRepository == null) meetingRepository = DI.createMeetingRepository();
        return meetingRepository;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}