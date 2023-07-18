package fr.ottobruc.p4_mareu.di;

import fr.ottobruc.p4_mareu.repository.MeetingRepository;
import fr.ottobruc.p4_mareu.service.DummyMeetingApiService;
import fr.ottobruc.p4_mareu.service.MeetingApiService;

/**
 * DI is a utility class that provides instances of MeetingRepository.
 * <p>
 * This class is used to manage the instances of meeting repositories in the application,
 * and to provide a new instance of MeetingApiService.
 * </p>
 */
public class DI {
    private static MeetingRepository meetingRepository;

    /**
     * Creates a new instance of MeetingRepository.
     *
     * @return a new instance of MeetingRepository.
     */
    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new DummyMeetingApiService());
    }

    /**
     * Returns the current instance of the MeetingRepository or creates a new one if it doesn't exist yet.
     *
     * @return the current instance of MeetingRepository.
     */
    public static MeetingRepository getMeetingRepository() {
        if (meetingRepository == null) meetingRepository = DI.createMeetingRepository();
        return meetingRepository;
    }

    /**
     * Always returns a new instance of MeetingApiService.
     * Useful for tests, to ensure the context is clean.
     *
     * @return a new instance of MeetingApiService.
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
