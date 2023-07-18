package fr.ottobruc.p4_mareu.model;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import fr.ottobruc.p4_mareu.utils.DateTimeUtil;

/**
 * The Meeting class represents a meeting object with all its associated details.
 */
public class Meeting {
    private int id;
    private Date startTime;
    private Date endTime;
    private Date date;
    private Room location;
    private String subject;
    private List<User> participants;

    /**
     * Construct a Meeting instance with provided details.
     *
     * @param id Identifier for the meeting.
     * @param startTime Time at which the meeting begins.
     * @param endTime Time at which the meeting ends.
     * @param date Date of the meeting.
     * @param location Location where the meeting takes place.
     * @param subject Subject or topic of the meeting.
     * @param participants List of Users who are attending the meeting.
     */
    public Meeting(int id, Date startTime, Date endTime, Date date, Room location, String subject, List<User> participants) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = participants;
    }

    /**
     * Construct a Meeting instance with provided details. Dates are parsed from String values.
     *
     * @param id Identifier for the meeting.
     * @param startTime Time at which the meeting begins as a String.
     * @param endTime Time at which the meeting ends as a String.
     * @param date Date of the meeting as a String.
     * @param location Location where the meeting takes place.
     * @param subject Subject or topic of the meeting.
     * @param participants List of Users who are attending the meeting.
     *
     * @throws ParseException if there is an error parsing the date or time strings.
     */
    public Meeting(int id, String startTime, String endTime, String date, Room location, String subject, List<User> participants) throws ParseException {
        this.id = id;
        this.startTime = DateTimeUtil.stringsToDate(date, startTime);
        this.endTime = DateTimeUtil.stringsToDate(date, endTime);
        this.date = DateTimeUtil.stringsToDate(date, null);
        this.location = location;
        this.subject = subject;
        this.participants = participants;
    }

    /**
     * @return Identifier for the meeting.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Date of the meeting.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return Location where the meeting takes place.
     */
    public Room getLocation() {
        return location;
    }

    /**
     * @return Subject or topic of the meeting.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return Time at which the meeting begins.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @return Time at which the meeting ends.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @return List of Users who are attending the meeting.
     */
    public List<User> getParticipants() {
        return participants;
    }

    /**
     * Sets the identifier for the meeting.
     *
     * @param id Identifier to be set for the meeting.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the start time for the meeting.
     *
     * @param startTime Start time to be set for the meeting.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time for the meeting.
     *
     * @param endTime End time to be set for the meeting.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the date for the meeting.
     *
     * @param date Date to be set for the meeting.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the location for the meeting.
     *
     * @param location Location to be set for the meeting.
     */
    public void setLocation(Room location) {
        this.location = location;
    }

    /**
     * Sets the subject for the meeting.
     *
     * @param subject Subject to be set for the meeting.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sets the participants for the meeting.
     *
     * @param participants Participants to be set for the meeting.
     */
    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}
